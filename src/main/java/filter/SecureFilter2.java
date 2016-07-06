package filter;

import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;
import ninja.Results;

public class SecureFilter2 implements Filter {

    /** If a username is saved we assume the session is valid */
    public final String USERNAME = "username";
    public final String PASSWORD = "iniAdalahAdmin";

    @Override
    public Result filter(FilterChain chain, Context context) {

        // if we got no cookies we break:
        if (context.getSession() == null
                || context.getSession().get(USERNAME) == null) {

            return Results.forbidden().html().template("/views/404.html");

        } else {
            return chain.next(context);
        }

    }
}
