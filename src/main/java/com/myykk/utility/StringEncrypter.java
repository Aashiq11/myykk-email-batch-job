package com.myykk.utility;


import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.as400.util.BASE64Decoder;
import com.ibm.as400.util.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
/**
 *  
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class StringEncrypter {
	public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
	public static final String DES_ENCRYPTION_SCHEME = "DES";
	//public static final String DEFAULT_ENCRYPTION_KEY	= "This is a fairly long phrase used to encrypt";

	private KeySpec				keySpec;
	private SecretKeyFactory	keyFactory;
	private Cipher				cipher;
	private static Log log = LogFactory.getLog(StringEncrypter.class);
    
	private static final String	UNICODE_FORMAT			= "UTF8";
	
	private static StringEncrypter stringEncrypter = null;
	private static String startPwdChar="";
	 /**
	 * Method getInstance.
	 * @return StringEncrypter
	 */
	
	public static synchronized StringEncrypter getInstance(String setKey)
	{
		log.debug("random Key in encrypt class="+setKey);
		log.debug("stringEncrypter..."+stringEncrypter);
		//if(stringEncrypter == null){
			try 
			{   
				log.debug("random Key in encrypt class="+setKey);
				startPwdChar=setKey;
				stringEncrypter = new StringEncrypter(StringEncrypter.DES_ENCRYPTION_SCHEME,getKeyChar(setKey));
			} 
			catch (EncryptionException e) 
			{
				stringEncrypter = null;
			}
		//}
		return stringEncrypter;
		
	}
	
	/*public StringEncrypter( String encryptionScheme ) throws EncryptionException
	{
		this( encryptionScheme, DEFAULT_ENCRYPTION_KEY );
	}
	*/
	/**
	 * Constructor for StringEncrypter.
	 * @param encryptionScheme String
	 * @param encryptionKey String
	 * @throws EncryptionException
	 */
	public static String getKeyChar(String setKey)
	{
		if("~".equals(setKey))
		{
			setKey="tekstreme1";
		}else if("@".equals(setKey))
		{
			setKey="tekstreme2";
		}else{
			setKey="tekstreme3";
		}
		return setKey;
	}
	public static String getRandomIntKey()
	{		 
        try{  
		int i = RandomUtils.nextInt(3);
           System.out.println("Generated Random No while logging out ... " + i);
           
		 if(i+1==1)
		 {
			return "~";					 
		 }
		 else if(i+1==2)
		 {
			 return "@";
		 }
		 else{
			return "^"; 
		 }
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
        }
        return "@";
	}
	public StringEncrypter( String encryptionScheme, String encryptionKey )
			throws EncryptionException
	{

		if ( encryptionKey == null )
				throw new IllegalArgumentException( "encryption key was null" );
		if ( encryptionKey.trim().length() <= 0 )
				throw new IllegalArgumentException(
						"encryption key has no characters" );

		try
		{
			byte[] keyAsBytes = encryptionKey.getBytes( UNICODE_FORMAT );

			if ( encryptionScheme.equals( DESEDE_ENCRYPTION_SCHEME) )
			{
				keySpec = new DESedeKeySpec( keyAsBytes );
			}
			else if ( encryptionScheme.equals( DES_ENCRYPTION_SCHEME ) )
			{
				keySpec = new DESKeySpec( keyAsBytes );
			}
			else
			{
				throw new IllegalArgumentException( "Encryption scheme not supported: "
													+ encryptionScheme );
			}

			keyFactory = SecretKeyFactory.getInstance( encryptionScheme );
			cipher = Cipher.getInstance( encryptionScheme );

		}
		catch (InvalidKeyException e)
		{
			log.debug("Error occured while instantiating StringEncrypter " + e.toString());			
			throw new EncryptionException( e );
		}
		catch (UnsupportedEncodingException e)
		{
			log.debug("Error occured while instantiating StringEncrypter " + e.toString());			
			throw new EncryptionException( e );
		}
		catch (NoSuchAlgorithmException e)
		{
			log.debug("Error occured while instantiating StringEncrypter " + e.toString());			
			throw new EncryptionException( e );
		}
		catch (NoSuchPaddingException e)
		{
			log.debug("Error occured while instantiating StringEncrypter " + e.toString());			
			throw new EncryptionException( e );
		}

	}
	public static String getEncrytedHintAnswer(String hintAnswr)
	{	
		StringBuffer profileQuery = new StringBuffer();
		 
		log.debug("Hint answer before encrypt.."+hintAnswr);
		StringBuffer baseList=new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ");
		
		StringBuffer subList1=new StringBuffer("5678901234mnopqrstuvwxyzabcdefghijklKLMNOPQRSTUVWXYZABCDEFGHIJ-");
		StringBuffer subList2=new StringBuffer("01234abcdefghijklmnopABCDEFGHIJqrstuvwxyz56789KLMNOPQRSTUVWXYZ-");
		StringBuffer subList3=new StringBuffer("abcdefghijklmIJKLMNOPQRSTUVWXYZ01234nopqrstuvwxyzABCDEFGH56789-");
		 
		
		String key=StringEncrypter.getRandomIntKey();
		log.debug("key is="+key);
		StringBuffer subList=new StringBuffer(""); 
		if("~".equals(key))
		{
			subList=subList1;
		}else if("@".equals(key))
		{
			subList=subList2;
		}
		else{
			subList=subList3;
		}
		String encryptCode=key;
		for(int i=0;i<hintAnswr.length();i++)
		{
			int charIndex=baseList.indexOf(String.valueOf(hintAnswr.charAt(i)));
			encryptCode=encryptCode+subList.charAt(charIndex);
		}
		log.debug("encryptCode"+encryptCode);
		return encryptCode;
	}
	public static String getDecryptedAnswer(String answer)
	{
	StringBuffer baseList=new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ");
	
	StringBuffer subList1=new StringBuffer("5678901234mnopqrstuvwxyzabcdefghijklKLMNOPQRSTUVWXYZABCDEFGHIJ-");
	StringBuffer subList2=new StringBuffer("01234abcdefghijklmnopABCDEFGHIJqrstuvwxyz56789KLMNOPQRSTUVWXYZ-");
	StringBuffer subList3=new StringBuffer("abcdefghijklmIJKLMNOPQRSTUVWXYZ01234nopqrstuvwxyzABCDEFGH56789-");
	
		char key=answer.charAt(0);
		//log.debug("length of sublist..="+subList1.toString().length());
		StringBuffer subList=new StringBuffer(); 
		//log.debug("key db="+key);
		if(key=='~')
		{
			//log.debug("subList1 assigning.."+key);
			subList=subList1;
		}else if(key=='@')
		{
			//log.debug("subList2 assigning.."+key);
			subList=subList2;
		}
		else{
			//log.debug("subList3 assigning=.."+key);
			subList=subList3;
		}
		String decryptCode="";
		for(int i=1;i<answer.length();i++)
		{
			//log.debug("cahr at "+i+" ="+answer.charAt(i));
			int charIndex=subList.toString().indexOf(answer.charAt(i));
			//log.debug("charIndex in for.."+charIndex);
			decryptCode=decryptCode+baseList.toString().charAt(charIndex);
		}
		//log.debug("decrypted code ="+decryptCode);
		return decryptCode;
	}
	

	/**
	 * This method append white space to make the length of string as multiple or 8
	 * @param pStrString String object
	 * @return String
	 */
	public String appendSpaces(String pStrString) {

		if (pStrString != null) {
			int strlen = pStrString.length();
			int nmod = 0;
			if ((strlen % 8) > 0) {
				nmod = 8 - (strlen % 8);
			} //if
			for (int i = 0; i < nmod; i++) {
				pStrString += " ";
			} //if
		}
		return pStrString;
	}

	/**
	 * Method encrypt.
	 * @param unencryptedString String
	 * @return String
	 * @throws EncryptionException
	 */
	public String encrypt( String unencryptedString ) throws EncryptionException
	{
		if ( unencryptedString == null || unencryptedString.trim().length() == 0 )
				throw new IllegalArgumentException(
						"unencrypted string was null or empty" );

		try
		{
			unencryptedString = appendSpaces(unencryptedString);
			SecretKey key = keyFactory.generateSecret( keySpec );
			cipher.init( Cipher.ENCRYPT_MODE, key );
			byte[] cleartext = unencryptedString.getBytes( UNICODE_FORMAT );
			byte[] ciphertext = cipher.doFinal( cleartext );

			BASE64Encoder base64encoder = new BASE64Encoder();
			System.out.println("encrypted value="+base64encoder.encodeBuffer( ciphertext ));
			return startPwdChar+base64encoder.encodeBuffer( ciphertext )+startPwdChar;
		}
		catch (Exception e)
		{
			log.debug("Error occured while encrypting the string: '" + unencryptedString + "'" + e.toString());						
			throw new EncryptionException( e );
		}
	}

	/**
	 * Method decrypt.
	 * @param encryptedString String
	 * @return String
	 * @throws EncryptionException
	 */
	public String decrypt( String encryptedString ) throws EncryptionException
	{
		if ( encryptedString == null || encryptedString.trim().length() <= 0 )
				throw new IllegalArgumentException( "encrypted string was null or empty" );

		try
		{
			SecretKey key = keyFactory.generateSecret( keySpec );
			cipher.init( Cipher.DECRYPT_MODE, key );
			BASE64Decoder base64decoder = new BASE64Decoder();
			byte[] cleartext = base64decoder.decodeBuffer( encryptedString );
			byte[] ciphertext = cipher.doFinal( cleartext );

			return bytes2String( ciphertext ).trim();
		}
		catch (Exception e)
		{
			log.debug("Error occured while decrypting the string: '" + encryptedString + "'" + e.toString());		
			throw new EncryptionException( e );
		}
	}

	/**
	 * Method bytes2String.
	 * @param bytes byte[]
	 * @return String
	 */
	private static String bytes2String( byte[] bytes )
	{
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < bytes.length; i++)
		{
			stringBuffer.append( (char) bytes[i] );
		}
		return stringBuffer.toString();
	}

	/**
	 */
	public class EncryptionException extends Exception
	{
		/**
		 * Constructor for EncryptionException.
		 * @param t Throwable
		 */
		public EncryptionException(Throwable t)
		{
			super(t.getMessage());
		}
	}
	
	/**
	 * Method main.
	 * @param args String[]
	 */
	public static void main(String[] args)
	{
		String passwd ="TestPwd";
		StringEncrypter stringEncrypter = StringEncrypter.getInstance("@");
		
		try 
		{
			String encryptedPasswd = stringEncrypter.encrypt(passwd);
			System.out.println("....$" + encryptedPasswd + "%");
			
			System.out.println("-----------------------");
			System.out.println("Decrypted Password: " + stringEncrypter.decrypt("Tf0RucbRQUDpMcTedvxWtA=="));						
		} 
		catch (EncryptionException e) 
		{			
			e.printStackTrace();
		}
	}
}
