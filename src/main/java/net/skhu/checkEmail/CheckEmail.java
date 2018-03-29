package net.skhu.checkEmail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CheckEmail {

    public static boolean checkEmail(String email) {

        Pattern p = Pattern.compile("(^[a-zA-Z0-9]+@[a-zA-Z.0-9]+$)");
        Matcher m = p.matcher(email);
        System.out.println(email);
        if(m.find())
        	return true;
        else
        	return false;
    }
}
