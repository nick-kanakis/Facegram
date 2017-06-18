package gr.personal.user.domain;

import java.io.Serializable;

/**
 * Generic JSON Object
 * @author nkanakis
 */
public class GenericJson implements Serializable {

    private static final long serialVersionUID = 1L;

    private String genericResult;
    private String errorMessage;
    private boolean exception = false;

    public GenericJson(String genericResult, String errorMessage, boolean exception) {
        this.genericResult = genericResult;
        this.errorMessage = errorMessage;
        this.exception = exception;
    }


    public GenericJson() {
    }

    public String getGenericResult() {return genericResult;}

    public void setGenericResult(String genericResult) {this.genericResult = genericResult;}

    public String getErrorMessage() {return errorMessage;}

    public void setErrorMessage(String errorMessage) {this.errorMessage = errorMessage;}

    public boolean isException() {return exception;}

    public void setException(boolean exception) {this.exception = exception;}

}
