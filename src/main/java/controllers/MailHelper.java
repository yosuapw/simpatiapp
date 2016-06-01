package controllers;

import ninja.postoffice.commonsmail.CommonsmailHelperImpl;

import org.apache.commons.mail.MultiPartEmail;

public class MailHelper extends CommonsmailHelperImpl {

    @Override
    public void doSetServerParameter(MultiPartEmail multiPartEmail, String smtpHost,
            Integer smtpPort, Boolean smtpSsl, String smtpUser, String smtpPassword,
            Boolean smtpDebug) {

        // /set config params:
        multiPartEmail.setHostName(smtpHost);
        multiPartEmail.setSmtpPort(smtpPort);
        multiPartEmail.setSSLOnConnect(smtpSsl);
        multiPartEmail.setStartTLSEnabled(true);

        if (smtpUser != null) {
            multiPartEmail.setAuthentication(smtpUser, smtpPassword);
        }

        multiPartEmail.setDebug(smtpDebug);
	}

}
