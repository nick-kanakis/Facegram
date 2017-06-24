package gr.facegram.gateway.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Nick Kanakis on 1/5/2017.
 */
@Component
public class AccessLogFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(AccessLogFilter.class);

    @Override
    public String filterType() {
        /*
        * Zuul has 4 types of standard filters
        *   > PRE filters are executed before the request is routed,
        *   > ROUTE filters can handle the actual routing of the request,
        *   > POST filters are executed after the request has been routed, and
        *   > ERROR filters execute if an error occurs in the course of handling the request.
        */
        return "post";
    }

    @Override
    public int filterOrder() {
        /* Filter order relative to other filters */
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        HttpServletResponse response = RequestContext.getCurrentContext().getResponse();

        logger.info("REQUEST :: < " + request.getScheme() + " " + request.getLocalAddr() + ":" + request.getLocalPort());
        logger.info("REQUEST :: < " + request.getMethod() + " " + request.getRequestURI() + " " + request.getProtocol());
        logger.info("RESPONSE:: > HTTP:" + response.getStatus());
        return null;
    }
}
