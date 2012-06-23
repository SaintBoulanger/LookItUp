<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>New AnalysisExpression</title>
            <link rel="stylesheet" type="text/css" href="/JsfJpaCrud/faces/jsfcrud.css" />
        </head>
        <body>
            <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            <h1>New AnalysisExpression</h1>
            <h:form>
                <h:inputHidden id="validateCreateField" validator="#{analysisExpression.validateCreate}" value="value"/>
                <h:panelGrid columns="2">
                    <h:outputText value="Expression:"/>
                    <h:inputText id="expression" value="#{analysisExpression.analysisExpression.expression}" title="Expression" required="true" requiredMessage="The expression field is required."/>
                </h:panelGrid>
                <br />
                <h:commandLink action="#{analysisExpression.create}" value="Create"/>
                <br />
                <br />
                <h:commandLink action="#{analysisExpression.listSetup}" value="Show All AnalysisExpression Items" immediate="true"/>
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />
            </h:form>
        </body>
    </html>
</f:view>
