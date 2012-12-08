package com.sourcesense.crl.script;

import org.alfresco.repo.jscript.BaseScopableProcessorExtension;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;


public class CRLUtilityScript extends BaseScopableProcessorExtension {

	public void sudo(final Function func, String runAsUsername) {
        final Context context = Context.getCurrentContext();
        final Scriptable scope = getScope();
        
        RunAsWork<Object> job = new RunAsWork<Object>(){
            @Override
            public Object doWork() throws Exception {
                func.call(context, scope, scope, new Object[]{});
                return null;
            }
        };
        
        AuthenticationUtil.runAs(job, runAsUsername);
    }
	
	
	public Object sudoWithArgs(final Function func, final Object[] args, String runAsUsername) {
        final Context context = Context.getCurrentContext();
        final Scriptable scope = getScope();
        
        RunAsWork<Object> job = new RunAsWork<Object>(){
            @Override
            public Object doWork() throws Exception {
           
                return func.call(context, scope, scope, args);
            }
        };
        
        return AuthenticationUtil.runAs(job, runAsUsername);
    }

	
}
