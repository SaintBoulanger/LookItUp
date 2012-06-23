package com.brigitte.lookitup.jsf;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import com.brigitte.lookitup.jpa.controllers.AnalysisExpressionJpaController;
import com.brigitte.lookitup.jpa.entities.AnalysisExpression;

/**
 *
 * @author mbohm
 */
public class AnalysisExpressionConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        Integer id = new Integer(string);
        AnalysisExpressionJpaController controller = (AnalysisExpressionJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "analysisExpressionJpa");
        return controller.findAnalysisExpression(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof AnalysisExpression) {
            AnalysisExpression o = (AnalysisExpression) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: jpa.entities.AnalysisExpression");
        }
    }

}
