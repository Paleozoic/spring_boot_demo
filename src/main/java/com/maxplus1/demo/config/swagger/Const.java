package com.maxplus1.demo.config.swagger;

public class Const {
    /**
     *
     swagger2使用说明：
     @Api：用在类上，说明该类的作用
     @ApiOperation：用在方法上，说明方法的作用
     @ApiIgnore：使用该注解忽略这个API
     @ApiImplicitParams：用在方法上包含一组参数说明
     @ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面
     paramType：参数放在哪个地方
         header-->@RequestHeader
         query-->@RequestParam
         path-->@PathVariable
         body-->@RequestBody
         form-->表单提交 request.getParam
     dataType：参数类型
     required：参数是否必须传
     value：参数的意思
     defaultValue：参数的默认值
     @ApiResponses：用于表示一组响应
     @ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息
     code：数字，例如400
     message：信息，例如"请求参数没填好"
     response：抛出异常的类
     @ApiModel：描述一个Model的信息（这种一般用在post创建的时候，使用@RequestBody这样的场景，请求参数无法使用@ApiImplicitParam注解进行描述的时候）
     @ApiModelProperty：描述一个model的属性
     */

    /**
     * paramType:
         header-->@RequestHeader
         query-->@RequestParam
         path-->@PathVariable
         body-->@RequestBody
         form-->表单提交 request.getParam
     */
    public static final String HEADER = "header";
    public static final String QUERY = "query";
    public static final String PATH = "path";
    public static final String BODY = "body";
    public static final String FORM = "form";

    /**
     * dataType:
        String
     */
    public static final String STRING = "string";
    public static final String INT = "int";
}
