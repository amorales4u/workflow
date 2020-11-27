package dev.c20.workflow.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.result.Output;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.Key;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
//import javax.servlet.http.HttpServletRequest;
//import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class StringUtils {
    /**
     * Returns a string representation for the given array using the given separator.<p>
     *
     * @param arg the array to transform to a String
     * @param separator the item separator
     *
     * @return the String of the given array
     */
    static public String arrayAsString(final String[] arg, String separator) {

        StringBuffer result = new StringBuffer();
        for (int i = 0; i < arg.length; i++) {
            result.append(arg[i]);
            if ((i + 1) < arg.length) {
                result.append(separator);
            }
        }
        return result.toString();
    }

    /**
     * Returns a string representation for the given collection using the given separator.<p>
     *
     * @param collection the collection to print
     * @param separator the item separator
     *
     * @return the string representation for the given collection
     */
    static public String collectionAsString(Collection<?> collection, String separator) {

        StringBuffer string = new StringBuffer(128);
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            string.append(it.next());
            if (it.hasNext()) {
                string.append(separator);
            }
        }
        return string.toString();
    }

    /**
     * Replaces occurrences of special control characters in the given input with 
     * a HTML representation.<p>
     *
     * This method currently replaces line breaks to <code>&ltbr/&gt</code> and special HTML chars 
     * like <code>&lt &gt &amp &quot</code> with their HTML entity representation.<p>
     *
     * @param source the String to escape
     *
     * @return the escaped String
     */
    static public String escapeHtml(String source) {
        if (source == null) {
            return null;
        }
        source = escapeXml(source);
        source = substitute(source, "\r", "");
        source = substitute(source, "\n", "<br/>\n");
        return source;
    }

    /**
     * Substitutes <code>searchString</code> in the given source String with <code>replaceString</code>.<p>
     *
     * This is a high-performance implementation which should be used as a replacement for 
     * <code>{@link String#replaceAll(java.lang.String, java.lang.String)}</code> in case no
     * regular expression evaluation is required.<p>
     *
     * @param source the content which is scanned
     * @param searchString the String which is searched in content
     * @param replaceString the String which replaces <code>searchString</code>
     *
     * @return the substituted String
     */
    static public String substitute(String source, String searchString, String replaceString) {

        if (source == null) {
            return null;
        }

        if (isEmpty(searchString)) {
            return source;
        }

        if (replaceString == null) {
            replaceString = "";
        }
        int len = source.length();
        int sl = searchString.length();
        int rl = replaceString.length();
        int length;
        if (sl == rl) {
            length = len;
        } else {
            int c = 0;
            int s = 0;
            int e;
            while ((e = source.indexOf(searchString, s)) != -1) {
                c++;
                s = e + sl;
            }
            if (c == 0) {
                return source;
            }
            length = len - (c * (sl - rl));
        }

        int s = 0;
        int e = source.indexOf(searchString, s);
        if (e == -1) {
            return source;
        }
        StringBuffer sb = new StringBuffer(length);
        while (e != -1) {
            sb.append(source.substring(s, e));
            sb.append(replaceString);
            s = e + sl;
            e = source.indexOf(searchString, s);
        }
        e = len;
        sb.append(source.substring(s, e));
        return sb.toString();
    }

    /**
     * Formats a resource name that it is displayed with the maximum length and path information is adjusted.<p>
     * In order to reduce the length of the displayed names, single folder names are removed/replaced with ... successively, 
     * starting with the second! folder. The first folder is removed as last.<p>
     *
     * Example: formatResourceName("/myfolder/subfolder/index.html", 21) returns <code>/myfolder/.../index.html</code>.<p>
     *
     * @param name the resource name to format
     * @param maxLength the maximum length of the resource name (without leading <code>/...</code>)
     *
     * @return the formatted resource name
     *
     */
    static public String formatResourceName(String name, int maxLength) {

        if (name == null) {
            return null;
        }

        if (name.length() <= maxLength) {
            return name;
        }

        int total = name.length();
        String[] names = splitAsArray(name, "/");
        if (name.endsWith("/")) {
            names[names.length - 1] = names[names.length - 1] + "/";
        }
        for (int i = 1; (total > maxLength) && (i < names.length - 1); i++) {
            if (i > 1) {
                names[i - 1] = "";
            }
            names[i] = "...";
            total = 0;
            for (int j = 0; j < names.length; j++) {
                int l = names[j].length();
                total += l + ((l > 0) ? 1 : 0);
            }
        }
        if (total > maxLength) {
            names[0] = (names.length > 2) ? "" : (names.length > 1) ? "..." : names[0];
        }

        StringBuffer result = new StringBuffer();
        for (int i = 0; i < names.length; i++) {
            if (names[i].length() > 0) {
                result.append("/");
                result.append(names[i]);
            }
        }

        return result.toString();
    }

    /**
     * Returns <code>true</code> if the provided String is either <code>null</code>
     * or the empty String <code>""</code>.<p> 
     *
     * @param value the value to check
     *
     * @return true, if the provided value is null or the empty String, false otherwise
     */
    static public boolean isEmpty(String value) {

        return (value == null) || (value.length() == 0);
    }

    /**
     * Returns <code>true</code> if the provided String is either <code>null</code>
     * or contains only white spaces.<p> 
     *
     * @param value the value to check
     *
     * @return true, if the provided value is null or contains only white spaces, false otherwise
     */
    static public boolean isEmptyOrWhitespaceOnly(String value) {

        return isEmpty(value) || (value.trim().length() == 0);
    }

    /**
     * Returns <code>true</code> if the provided String is neither <code>null</code>
     * nor the empty String <code>""</code>.<p> 
     *
     * @param value the value to check
     *
     * @return true, if the provided value is not null and not the empty String, false otherwise
     */
    static public boolean isNotEmpty(String value) {

        return (value != null) && (value.length() != 0);
    }

    /**
     * Returns <code>true</code> if the provided String is neither <code>null</code>
     * nor contains only white spaces.<p> 
     *
     * @param value the value to check
     *
     * @return <code>true</code>, if the provided value is <code>null</code> 
     *          or contains only white spaces, <code>false</code> otherwise
     */
    static public boolean isNotEmptyOrWhitespaceOnly(String value) {

        return (value != null) && (value.trim().length() > 0);
    }

    /**
     * Concatenates multiple paths and separates them with '/'.<p>
     *
     * Consecutive slashes will be reduced to a single slash in the resulting string.
     * For example, joinPaths("/foo/", "/bar", "baz") will return "/foo/bar/baz".
     *
     * @param paths the array of paths
     *
     * @return the joined path 
     */
    static public String joinPaths(String... paths) {

        String result = listAsString(Arrays.asList(paths), "/");
        // result may now contain multiple consecutive slashes, so reduce them to single slashes
        result = result.replaceAll("/+", "/");
        return result;
    }

    /**
     * Returns a string representation for the given list using the given separator.<p>
     *
     * @param <E> type of list entries
     * @param list the list to write
     * @param separator the item separator string
     *
     * @return the string representation for the given map
     */
    static public <E> String listAsString(List<E> list, String separator) {

        StringBuffer string = new StringBuffer(128);
        Iterator<E> it = list.iterator();
        while (it.hasNext()) {
            string.append(it.next());
            if (it.hasNext()) {
                string.append(separator);
            }
        }
        return string.toString();
    }

    /**
     *
     * @param str the string to split
     * @param splitter the splitter string
     *
     * @return the splitted string
     */
    static public String[] splitAsArray(String str, String splitter) {

        return str.split(splitter);
    }

    /**
     * Splits a String into substrings along the provided char delimiter and returns
     * the result as a List of Substrings.<p>
     *
     * @param source the String to split
     * @param delimiter the delimiter to split at
     * @param trim flag to indicate if leading and trailing white spaces should be omitted
     *
     * @return the List of splitted Substrings
     */
    static public List<String> splitAsList(String source, char delimiter, boolean trim) {

        List<String> result = new ArrayList<>();
        int i = 0;
        int l = source.length();
        int n = source.indexOf(delimiter);
        while (n != -1) {
            // zero - length items are not seen as tokens at start or end
            if ((i < n) || ((i > 0) && (i < l))) {
                result.add(trim ? source.substring(i, n).trim() : source.substring(i, n));
            }
            i = n + 1;
            n = source.indexOf(delimiter, i);
        }
        // is there a non - empty String to cut from the tail? 
        if (n < 0) {
            n = source.length();
        }
        if (i < n) {
            result.add(trim ? source.substring(i).trim() : source.substring(i));
        }
        return result;
    }

    /**
     * Splits a String into substrings along the provided String delimiter and returns
     * the result as List of Substrings.<p>
     *
     * @param source the String to split
     * @param delimiter the delimiter to split at
     *
     * @return the Array of splitted Substrings
     */
    static public List<String> splitAsList(String source, String delimiter) {

        return splitAsList(source, delimiter, false);
    }

    /**
     * Splits a String into substrings along the provided String delimiter and returns
     * the result as List of Substrings.<p>
     *
     * @param source the String to split
     * @param delimiter the delimiter to split at
     * @param trim flag to indicate if leading and trailing white spaces should be omitted
     *
     * @return the Array of splitted Substrings
     */
    static public List<String> splitAsList(String source, String delimiter, boolean trim) {

        int dl = delimiter.length();
        if (dl == 1) {
            // optimize for short strings
            return splitAsList(source, delimiter.charAt(0), trim);
        }

        List<String> result = new ArrayList<String>();
        int i = 0;
        int l = source.length();
        int n = source.indexOf(delimiter);
        while (n != -1) {
            // zero - length items are not seen as tokens at start or end:  ",," is one empty token but not three
            if ((i < n) || ((i > 0) && (i < l))) {
                result.add(trim ? source.substring(i, n).trim() : source.substring(i, n));
            }
            i = n + dl;
            n = source.indexOf(delimiter, i);
        }
        // is there a non - empty String to cut from the tail? 
        if (n < 0) {
            n = source.length();
        }
        if (i < n) {
            result.add(trim ? source.substring(i).trim() : source.substring(i));
        }
        return result;
    }

    /**
     * Checks whether one path is a prefix path of another, i.e. its path components are 
     * the initial path components of the second path.<p>
     *
     * It is not enough to just use {@link String#startsWith}, because we want /foo/bar to 
     * be a prefix path of  /foo/bar/baz, but not of /foo/bar42.<p>
     *
     * @param firstPath the first path 
     * @param secondPath the second path 
     *
     * @return true if the first path is a prefix path of the second path 
     */
    static public boolean isPrefixPath(String firstPath, String secondPath) {

        firstPath = joinPaths(firstPath, "/");
        secondPath = joinPaths(secondPath, "/");
        return secondPath.startsWith(firstPath);
    }



    /**
     * Splits a String into substrings along the provided <code>paramDelim</code> delimiter,
     * then each substring is treat as a key-value pair delimited by <code>keyValDelim</code>.<p>
     *
     * @param source the string to split
     * @param paramDelim the string to delimit each key-value pair
     * @param keyValDelim the string to delimit key and value
     *
     * @return a map of splitted key-value pairs
     */
    static public Map<String, String> splitAsMap(String source, String paramDelim, String keyValDelim) {

        int keyValLen = keyValDelim.length();
        // use LinkedHashMap to preserve the order of items 
        Map<String, String> params = new LinkedHashMap<String, String>();
        Iterator<String> itParams = splitAsList(source, paramDelim, true).iterator();
        while (itParams.hasNext()) {
            String param = itParams.next();
            int pos = param.indexOf(keyValDelim);
            String key = param;
            String value = "";
            if (pos > 0) {
                key = param.substring(0, pos);
                if (pos + keyValLen < param.length()) {
                    value = param.substring(pos + keyValLen);
                }
            }
            params.put(key, value);
        }
        return params;
    }

    /**
     * Escapes a String so it may be printed as text content or attribute
     * value in a HTML page or an XML file.<p>
     *
     * This method replaces the following characters in a String:
     * <ul>
     * <li><b>&lt</b> with &amplt
     * <li><b>&gt</b> with &ampgt
     * <li><b>&amp</b> with &ampamp
     * <li><b>&quot</b> with &ampquot
     * </ul><p>
     *
     * @param source the string to escape
     * @param  , all entities that already are escaped are left untouched
     *
     * @return the escaped string
     */
    static public String escapeXml(String source) {
        if (source == null) {
            return null;
        }
        StringBuffer result = new StringBuffer(source.length() * 2);

        for (int i = 0; i < source.length(); ++i) {
            char ch = source.charAt(i);
            switch (ch) {
                case '<':
                    result.append("&lt");
                    break;
                case '>':
                    result.append("&gt");
                    break;
                case '&':
                    // don't escape already escaped international and special characters
                    int terminatorIndex = source.indexOf("", i);
                    if (terminatorIndex > 0) {
                        if (source.substring(i + 1, terminatorIndex).matches("#[0-9]+")) {
                            result.append(ch);
                            break;
                        }
                    }

                    // note that to other "break" in the above "if" block
                    result.append("&amp");
                    break;
                case '"':
                    result.append("&quot");
                    break;
                default:
                    result.append(ch);
            }
        }
        return new String(result);
    }

    static public boolean comparePaths(String path1, String path2) {

        return addLeadingAndTrailingSlash(path1).equals(addLeadingAndTrailingSlash(path2));
    }

    static public String addLeadingAndTrailingSlash(String path) {

        StringBuffer buffer1 = new StringBuffer();
        if (!path.startsWith("/")) {
            buffer1.append("/");
        }
        buffer1.append(path);
        if (!path.endsWith("/")) {
            buffer1.append("/");
        }
        return buffer1.toString();
    }

    public static String SHA256(String text, int length ) throws Exception {
        String result;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(text.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        byte[] digest = md.digest();
        for( byte b : digest ) {
            sb.append( String.format("%02x",b)); // Convert to HEX
        }
        if( length > sb.toString().length() ) {
            result = sb.toString();
        } else {
            result = sb.toString().substring(0,length);
        }
        return result;
    }
/*
    public static String getPathFromURI( String basePath, HttpServletRequest request) {
        try {
            String path = request.getRequestURI().substring((request.getContextPath() + basePath).length());
            return URLDecoder.decode(path, "UTF-8");
        } catch( Exception ex ) {
            return null;
        }
    }
*/
    public static Object JSONFromString( String data ) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(data,Object.class);

    }

    public static String toJSON( Object map ) throws Exception {
        return toJSON( map, false );
    }

    public static String toJSON( Object map, Boolean pretty ) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = null;
        if (pretty)
            jsonResult = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(map);
        else
            jsonResult = mapper.writer().writeValueAsString(map);

        return jsonResult;
    }

    private static SecretKeySpec secretKey;
    private static byte[] key;

    public static void setKey(String myKey)
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String strToEncrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e)
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    public static ByteArrayOutputStream encrypt(InputStream strToEncrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            CipherOutputStream cos = new CipherOutputStream(output, cipher);
            byte[] block = new byte[32];
            int i;
            while ((i = strToEncrypt.read(block)) != -1) {
                cos.write(block, 0, i);
            }
            cos.close();
            return output;
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static ByteArrayOutputStream decrypt(InputStream strToDecrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            CipherOutputStream cos = new CipherOutputStream(output, cipher);
            byte[] block = new byte[32];
            int i;
            while ((i = strToDecrypt.read(block)) != -1) {
                cos.write(block, 0, i);
            }
            cos.close();
            return output;
        }
        catch (Exception e)
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    static public void main(String[] args) throws Exception {

        //final String secretKey = "ssshhhhhhhhhhh!!!!";
        final String secretKey = "Ch1sasComoAsiSiJalaCon una LLave mas laraga";

        String originalString = "howtodoinjava.com";
        String encryptedString = encrypt(originalString, secretKey) ;
        String decryptedString = decrypt(encryptedString, secretKey) ;

        System.out.println(originalString);
        System.out.println(encryptedString);
        System.out.println(decryptedString);



    }

}
