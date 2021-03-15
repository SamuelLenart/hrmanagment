package sk.kosickaakademia.lenart;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class Encrypt {

    private String keyWordString;
    private String removeDuplicates(String word){

        for (int i = 0; i < word.length(); i++) {
            if(!keyWordString.contains(String.valueOf(word.charAt(i)))) {
                keyWordString += String.valueOf(word.charAt(i));
            }
        }
        return(keyWordString);
    }


    public Encrypt(String aKeyWord)
    {
        keyWordString = removeDuplicates(keyWordString);
        keyWordString = aKeyWord;
        int moreChar = 26 - keyWordString.length();
        char ch = 'Z';
        for(int j=0; j<moreChar; j++)
        {
            keyWordString += ch;
            ch -= 1;
        }
        System.out.println("The mapping string is: " + keyWordString);
    }

    public void encryptStream(InputStream in, OutputStream out)
            throws IOException
    {
        boolean done = false;
        while (!done)
        {
            int next = in.read();
            if (next == -1)
            {
                done = true;
            }
            else
            {
                int encrypted = encryptWordKey(next);
                System.out.println((char)next + " is encrypted to " + (char)encrypted);
                out.write(encrypted);
            }
        }
    }


    public int encryptWordKey(int b)
    {
        int pos = b % 65;
        return keyWordString.charAt(pos);
    }
}