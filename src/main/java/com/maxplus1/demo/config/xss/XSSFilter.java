package com.maxplus1.demo.config.xss;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class XSSFilter extends OncePerRequestFilter {
    private String exclude = null;  //不需要过滤的路径集合
    private Pattern pattern = null;  //匹配不需要过滤路径的正则表达式

    /*private static List<Object[]> getXssPatternList() {
        List<Object[]> ret = new ArrayList<Object[]>();

        ret.add(new Object[]{"<(no)?script[^>]*>.*?</(no)?script>", Pattern.CASE_INSENSITIVE});
        ret.add(new Object[]{"eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});
        ret.add(new Object[]{"expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});
        ret.add(new Object[]{"(javascript:|vbscript:|view-source:)*", Pattern.CASE_INSENSITIVE});
        ret.add(new Object[]{"<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});
        ret.add(new Object[]{"(window\\.location|window\\.|\\.location|document\\.cookie|document\\.|alert\\(.*?\\)|window\\.open\\()*", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});
        ret.add(new Object[]{"<+\\s*\\w*\\s*(oncontrolselect|oncopy|oncut|ondataavailable|ondatasetchanged|ondatasetcomplete|ondblclick|ondeactivate|ondrag|ondragend|ondragenter|ondragleave|ondragover|ondragstart|ondrop|onerror=|onerroupdate|onfilterchange|onfinish|onfocus|onfocusin|onfocusout|onhelp|onkeydown|onkeypress|onkeyup|onlayoutcomplete|onload|onlosecapture|onmousedown|onmouseenter|onmouseleave|onmousemove|onmousout|onmouseover|onmouseup|onmousewheel|onmove|onmoveend|onmovestart|onabort|onactivate|onafterprint|onafterupdate|onbefore|onbeforeactivate|onbeforecopy|onbeforecut|onbeforedeactivate|onbeforeeditocus|onbeforepaste|onbeforeprint|onbeforeunload|onbeforeupdate|onblur|onbounce|oncellchange|onchange|onclick|oncontextmenu|onpaste|onpropertychange|onreadystatechange|onreset|onresize|onresizend|onresizestart|onrowenter|onrowexit|onrowsdelete|onrowsinserted|onscroll|onselect|onselectionchange|onselectstart|onstart|onstop|onsubmit|onunload)+\\s*=+", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});
        return ret;
    }*/

    public void setExclude(String exclude) {
        this.exclude = exclude;
        pattern = Pattern.compile(getRegStr(exclude));
    }

    /**
     * XSS过滤
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if(StringUtils.isNotBlank(requestURI)){
            requestURI = requestURI.replace(request.getContextPath(),"");
        }
        if(pattern!=null&&pattern.matcher(requestURI).matches()){
            filterChain.doFilter(request, response);
        }else{
            EscapeScriptwrapper escapeScriptwrapper = new EscapeScriptwrapper(request);
            filterChain.doFilter(escapeScriptwrapper, response);
        }
    }

    /**
     * 将传递进来的不需要过滤得路径集合的字符串格式化成一系列的正则规则
     * @param str 不需要过滤的路径集合
     * @return 正则表达式规则
     * */
    private String getRegStr(String str){
        if(StringUtils.isNotBlank(str)){
            String[] excludes = str.split(";");  //以分号进行分割
            int length = excludes.length;
            for(int i=0;i<length;i++){
                String tmpExclude = excludes[i];
                //对点、反斜杠和星号进行转义
                tmpExclude = tmpExclude.replace("\\", "\\\\").replace(".", "\\.").replace("*", ".*");

                tmpExclude = "^" + tmpExclude + "$";
                excludes[i] = tmpExclude;
            }
            return StringUtils.join(excludes, "|");
        }
        return str;
    }

    /**
     * 继承HttpServletRequestWrapper，创建装饰类，以达到修改HttpServletRequest参数的目的
     * */
    private class EscapeScriptwrapper extends HttpServletRequestWrapper{
        private Map<String, String[]> parameterMap;  //所有参数的Map集合
        public EscapeScriptwrapper(HttpServletRequest request) {
            super(request);
            parameterMap = request.getParameterMap();
        }

        //重写几个HttpServletRequestWrapper中的方法
        /**
         * 获取所有参数名
         * @return 返回所有参数名
         * */
        @Override
        public Enumeration<String> getParameterNames() {
            Vector<String> vector = new Vector<String>(parameterMap.keySet());
            return vector.elements();
        }

        /**
         * 获取指定参数名的值，如果有重复的参数名，则返回第一个的值
         * 接收一般变量 ，如text类型
         *
         * @param name 指定参数名
         * @return 指定参数名的值
         * */
        @Override
        public String getParameter(String name) {
            String[] results = parameterMap.get(name);
            if(results == null || results.length <= 0)
                return null;
            else{
                return escapeXSS(results[0]);
            }
        }

        /**
         * 获取指定参数名的所有值的数组，如：checkbox的所有数据
         * 接收数组变量 ，如checkobx类型
         * */
        @Override
        public String[] getParameterValues(String name) {
            String[] results = parameterMap.get(name);
            if(results == null || results.length <= 0)
                return null;
            else{
                int length = results.length;
                for(int i=0;i<length;i++){
                    results[i] = escapeXSS(results[i]);
                }
                return results;
            }
        }

        /**
         * 过滤字符串中的js脚本
         * 解码：StringEscapeUtils.unescapeXml(escapedStr)
         * */
        private String escapeXSS(String str){
            str = StringEscapeUtils.escapeXml(str);

            Pattern tmpPattern = Pattern.compile("[sS][cC][rR][iI][pP][tT]");
            Matcher tmpMatcher = tmpPattern.matcher(str);
            if(tmpMatcher.find()){
                str = tmpMatcher.replaceAll(tmpMatcher.group(0) + "\\\\");
            }
            return str;
        }
    }

}
