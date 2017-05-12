package anwendungskern.kunden_komponente.datatypes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Heinrich on 28.11.2015.
 */
public class EmailTyp implements Serializable{

    public final String mail;

    public EmailTyp(String mail){
        this.mail = mail;
    }

    public boolean equals(Object o){
        if(o == this) return true;
        if(!(o instanceof EmailTyp)) return false;

        EmailTyp e = (EmailTyp) o;
        return this.mail.equals(e.mail);
    }
}
