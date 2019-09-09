package com.lucifaer.expTest.controller;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;
import org.mvel2.MVEL;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {
    @RequestMapping(value = {"/spel", "/spel.php"})
    @ResponseBody
    public String spel(String exp, HttpServletResponse response) {
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(exp);
        return expression.getValue().toString();
    }

    @RequestMapping(value = {"/mvel", "/mvel.php"})
    @ResponseBody
    public String mvel(String exp, HttpServletResponse response) {
        return MVEL.eval(exp).toString();
    }

    @RequestMapping(value = {"/ognl", "/ognl.php"})
    @ResponseBody
    public String ognl(String exp, HttpServletResponse response) throws OgnlException {
        OgnlContext context = new OgnlContext();
        return Ognl.getValue(exp, context, context.getRoot()).toString();
    }
}
