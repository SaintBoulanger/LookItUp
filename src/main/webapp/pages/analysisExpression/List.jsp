<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Listing AnalysisExpression Items</title>
            <link rel="stylesheet" type="text/css" href="/JsfJpaCrud/faces/jsfcrud.css" />
        </head>
        <body>
            <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            <h1>Listing AnalysisExpression Items</h1>
            <h:form styleClass="jsfcrud_list_form">
                <h:outputText escape="false" value="(No AnalysisExpression Items Found)<br />" rendered="#{analysisExpression.pagingInfo.itemCount == 0}" />
                <h:panelGroup rendered="#{analysisExpression.pagingInfo.itemCount > 0}">
                    <h:outputText value="Item #{analysisExpression.pagingInfo.firstItem + 1}..#{analysisExpression.pagingInfo.lastItem} of #{analysisExpression.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{analysisExpression.prev}" value="Previous #{analysisExpression.pagingInfo.batchSize}" rendered="#{analysisExpression.pagingInfo.firstItem >= analysisExpression.pagingInfo.batchSize}"/>&nbsp;
                    <h:commandLink action="#{analysisExpression.next}" value="Next #{analysisExpression.pagingInfo.batchSize}" rendered="#{analysisExpression.pagingInfo.lastItem + analysisExpression.pagingInfo.batchSize <= analysisExpression.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{analysisExpression.next}" value="Remaining #{analysisExpression.pagingInfo.itemCount - analysisExpression.pagingInfo.lastItem}"
                                   rendered="#{analysisExpression.pagingInfo.lastItem < analysisExpression.pagingInfo.itemCount && analysisExpression.pagingInfo.lastItem + analysisExpression.pagingInfo.batchSize > analysisExpression.pagingInfo.itemCount}"/>
                    <h:dataTable value="#{analysisExpression.analysisExpressionItems}" var="item" border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px">
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
                                <f:param name="jsfcrud.currentAnalysisExpression" value="#{jsfcrud_class['com.brigitte.lookitup.jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][analysisExpression.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{analysisExpression.editSetup}">
                                <f:param name="jsfcrud.currentAnalysisExpression" value="#{jsfcrud_class['com.brigitte.lookitup.jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][analysisExpression.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{analysisExpression.destroy}">
                                <f:param name="jsfcrud.currentAnalysisExpression" value="#{jsfcrud_class['com.brigitte.lookitup.jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][analysisExpression.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                        </h:column>
                    </h:dataTable>
                </h:panelGroup>
                <br />
                <h:commandLink action="#{analysisExpression.createSetup}" value="New AnalysisExpression"/>
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />
                
            </h:form>
        </body>
    </html>
</f:view>
