package utils;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author MartinSaman
 */
public class NetData {

    private final static HttpServletRequest MYSERVLET = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

    public static String getIP() throws Exception {
        return MYSERVLET.getServerName();
    }

    public static int getPORT() throws Exception {
        return MYSERVLET.getServerPort();
    }
}
