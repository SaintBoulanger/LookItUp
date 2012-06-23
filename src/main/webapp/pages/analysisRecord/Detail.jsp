<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>AnalysisRecord Detail</title>
            <link rel="stylesheet" type="text/css" href="/JsfJpaCrud/faces/jsfcrud.css" />
        </head>
        <body>
            <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            <h1>AnalysisRecord Detail</h1>
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText value="Name:"/>
                    <h:outputText value="#{analysisRecord.analysisRecord.name}" title="Name" />
                    <h:outputText value="AnalysisExpressionCollection:" />
                    <h:panelGroup>
                        <h:outputText rendered="#{empty analysisRecord.analysisRecord.analysisExpressionCollection}" value="(No Items)"/>
                        <h:dataTable value="#{analysisRecord.analysisRecord.analysisExpressionCollection}" var="item" 
                                     border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px" 
                                     rendered="#{not empty analysisRecord.analysisRecord.analysisExpressionCollection}">
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Expression"/>
                                </f:facet>
                                <h:outputText value=" #{item.expression}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText escape="false" value="&nbsp;"/>
                                </f:facet>
                                <h:commandLink value="Show" action="#{analysisExpression.detailSetup}">
                                    <f:param name="jsfcrud.currentAnalysisRecord" value="#{jsfcrud_class['com.brigitte.lookitup.jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][analysisRecord.analysisRecord][analysisRecord.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentAnalysisExpression" value="#{jsfcrud_class['com.brigitte.lookitup.jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][analysisExpression.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="analysisRecord" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.AnalysisRecordController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Edit" action="#{analysisExpression.editSetup}">
                                    <f:param name="jsfcrud.currentAnalysisRecord" value="#{jsfcrud_class['com.brigitte.lookitup.jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][analysisRecord.analysisRecord][analysisRecord.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentAnalysisExpression" value="#{jsfcrud_class['com.brigitte.lookitup.jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][analysisExpression.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="analysisRecord" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.AnalysisRecordController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Destroy" action="#{analysisExpression.destroy}">
                                    <f:param name="jsfcrud.currentAnalysisRecord" value="#{jsfcrud_class['com.brigitte.lookitup.jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][analysisRecord.analysisRecord][analysisRecord.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentAnalysisExpression" value="#{jsfcrud_class['com.brigitte.lookitup.jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][analysisExpression.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="analysisRecord" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.AnalysisRecordController" />
                                </h:commandLink>
                            </h:column>
                        </h:dataTable>
                    </h:panelGroup>
                </h:panelGrid>
                <br />
                <h:commandLink action="#{analysisRecord.destroy}" value="Destroy">
                    <f:param name="jsfcrud.currentAnalysisRecord" value="#{jsfcrud_class['com.brigitte.lookitup.jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][analysisRecord.analysisRecord][analysisRecord.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <br />
                <h:commandLink action="#{analysisRecord.editSetup}" value="Edit">
                    <f:param name="jsfcrud.currentAnalysisRecord" value="#{jsfcrud_class['com.brigitte.lookitup.jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][analysisRecord.analysisRecord][analysisRecord.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <h:commandLink action="#{analysisRecord.createSetup}" value="New AnalysisRecord" />
                <br />
                <h:commandLink action="#{analysisRecord.listSetup}" value="Show All AnalysisRecord Items"/>
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />
            </h:form>
        </body>
    </html>
</f:view>
