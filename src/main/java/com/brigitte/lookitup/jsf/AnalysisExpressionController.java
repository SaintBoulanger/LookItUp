package com.brigitte.lookitup.jsf;

import com.brigitte.lookitup.jpa.controllers.AnalysisExpressionJpaController;
import com.brigitte.lookitup.jpa.controllers.exceptions.NonexistentEntityException;
import com.brigitte.lookitup.jpa.entities.AnalysisExpression;
import com.brigitte.lookitup.jsf.util.JsfUtil;
import com.brigitte.lookitup.jsf.util.PagingInfo;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

/**
 *
 * @author mbohm
 */
public class AnalysisExpressionController {

    public AnalysisExpressionController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (AnalysisExpressionJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "analysisExpressionJpa");
        pagingInfo = new PagingInfo();
        converter = new AnalysisExpressionConverter();
    }
    private AnalysisExpression analysisExpression = null;
    private List<AnalysisExpression> analysisExpressionItems = null;
    private AnalysisExpressionJpaController jpaController = null;
    private AnalysisExpressionConverter converter = null;
    private PagingInfo pagingInfo = null;

    public PagingInfo getPagingInfo() {
        if (pagingInfo.getItemCount() == -1) {
            pagingInfo.setItemCount(jpaController.getAnalysisExpressionCount());
        }
        return pagingInfo;
    }

    public SelectItem[] getAnalysisExpressionItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findAnalysisExpressionEntities(), false);
    }

    public SelectItem[] getAnalysisExpressionItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findAnalysisExpressionEntities(), true);
    }

    public AnalysisExpression getAnalysisExpression() {
        if (analysisExpression == null) {
            analysisExpression = (AnalysisExpression) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentAnalysisExpression", converter, null);
        }
        if (analysisExpression == null) {
            analysisExpression = new AnalysisExpression();
        }
        return analysisExpression;
    }

    public String listSetup() {
        reset(true);
        return "analysisExpression_list";
    }

    public String createSetup() {
        reset(false);
        analysisExpression = new AnalysisExpression();
        return "analysisExpression_create";
    }

    public String create() {
        try {
            jpaController.create(analysisExpression);
            JsfUtil.addSuccessMessage("AnalysisExpression was successfully created.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("analysisExpression_detail");
    }

    public String editSetup() {
        return scalarSetup("analysisExpression_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        analysisExpression = (AnalysisExpression) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentAnalysisExpression", converter, null);
        if (analysisExpression == null) {
            String requestAnalysisExpressionString = JsfUtil.getRequestParameter("jsfcrud.currentAnalysisExpression");
            JsfUtil.addErrorMessage("The analysisExpression with id " + requestAnalysisExpressionString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        String analysisExpressionString = converter.getAsString(FacesContext.getCurrentInstance(), null, analysisExpression);
        String currentAnalysisExpressionString = JsfUtil.getRequestParameter("jsfcrud.currentAnalysisExpression");
        if (analysisExpressionString == null || analysisExpressionString.length() == 0 || !analysisExpressionString.equals(currentAnalysisExpressionString)) {
            String outcome = editSetup();
            if ("analysisExpression_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Could not edit analysisExpression. Try again.");
            }
            return outcome;
        }
        try {
            jpaController.edit(analysisExpression);
            JsfUtil.addSuccessMessage("AnalysisExpression was successfully updated.");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return listSetup();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return detailSetup();
    }

    public String destroy() {
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentAnalysisExpression");
        Integer id = new Integer(idAsString);
        try {
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("AnalysisExpression was successfully deleted.");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return relatedOrListOutcome();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return relatedOrListOutcome();
    }

    private String relatedOrListOutcome() {
        String relatedControllerOutcome = relatedControllerOutcome();
        if (relatedControllerOutcome != null) {
            return relatedControllerOutcome;
        }
        return listSetup();
    }

    public List<AnalysisExpression> getAnalysisExpressionItems() {
        if (analysisExpressionItems == null) {
            getPagingInfo();
            analysisExpressionItems = jpaController.findAnalysisExpressionEntities(pagingInfo.getBatchSize(), pagingInfo.getFirstItem());
        }
        return analysisExpressionItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "analysisExpression_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "analysisExpression_list";
    }

    private String relatedControllerOutcome() {
        String relatedControllerString = JsfUtil.getRequestParameter("jsfcrud.relatedController");
        String relatedControllerTypeString = JsfUtil.getRequestParameter("jsfcrud.relatedControllerType");
        if (relatedControllerString != null && relatedControllerTypeString != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            Object relatedController = context.getApplication().getELResolver().getValue(context.getELContext(), null, relatedControllerString);
            try {
                Class<?> relatedControllerType = Class.forName(relatedControllerTypeString);
                Method detailSetupMethod = relatedControllerType.getMethod("detailSetup");
                return (String) detailSetupMethod.invoke(relatedController);
            } catch (ClassNotFoundException e) {
                throw new FacesException(e);
            } catch (NoSuchMethodException e) {
                throw new FacesException(e);
            } catch (IllegalAccessException e) {
                throw new FacesException(e);
            } catch (InvocationTargetException e) {
                throw new FacesException(e);
            }
        }
        return null;
    }

    private void reset(boolean resetFirstItem) {
        analysisExpression = null;
        analysisExpressionItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        AnalysisExpression newAnalysisExpression = new AnalysisExpression();
        String newAnalysisExpressionString = converter.getAsString(FacesContext.getCurrentInstance(), null, newAnalysisExpression);
        String analysisExpressionString = converter.getAsString(FacesContext.getCurrentInstance(), null, analysisExpression);
        if (!newAnalysisExpressionString.equals(analysisExpressionString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }

}
