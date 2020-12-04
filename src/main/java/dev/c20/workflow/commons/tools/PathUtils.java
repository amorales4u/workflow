package dev.c20.workflow.commons.tools;

public class PathUtils {

    static public boolean isFolder(String resource) {

        if( StringUtils.isEmpty(resource) ) {
            return false;
        }
        return resource.endsWith("/");
    }

    static public boolean isFile(String resource) {

        if( StringUtils.isEmpty(resource) ) {
            return false;
        }
        return !resource.endsWith("/");
    }

    static public String getName(String resource) {

        if ("/" == resource) {
            return "/";
        }
        // remove the last char, for a folder this will be "/", for a file it does not matter
        String parent = (resource.substring(0, resource.length() - 1));
        // now as the name does not end with "/", check for the last "/" which is the parent folder name
        return resource.substring(parent.lastIndexOf('/') + 1);
    }

    static public String getExtension(String resource) {

        if( isFolder(resource) ) {
            return null;
        }
        String name = getName(resource);

        if( name.indexOf(".") == -1 ) {
            return null;
        }

        return name.substring(name.indexOf(".")+1);
    }

    static public String getFolderPath(String resource) {

        return resource.substring(0, resource.lastIndexOf('/') + 1);
    }

    static public String getParentFolder(String resource) {

        if ( StringUtils.isEmptyOrWhitespaceOnly(resource) || resource == "/" ) {
            return null;
        }
        // remove the last char, for a folder this will be "/", for a file it does not matter
        String parent = (resource.substring(0, resource.length() - 1));
        // now as the name does not end with "/", check for the last "/" which is the parent folder name
        return parent.substring(0, parent.lastIndexOf('/') + 1);
    }

    static public int getPathLevel(String resource) {

        int level = -1;
        int pos = 0;
        while (resource.indexOf('/', pos) >= 0) {
            pos = resource.indexOf('/', pos) + 1;
            level++;
        }
        if( resource.endsWith("/") ) {
            level--;
        }
        return level;
    }

    static public String getPathFromLevel(String resource, int level) {
        String result = getPathPart(resource,level-1);
        return "/" + resource.substring(result.length());
    }

    static public String getPathPart(String resource, int level) {

        resource = getFolderPath(resource);
        String result = null;
        int pos = 0, count = 0;
        if (level >= 0) {
            // Walk down from the root folder /
            while ((count < level) && (pos > -1)) {
                count++;
                pos = resource.indexOf('/', pos + 1);
            }
        } else {
            // Walk up from the current folder
            pos = resource.length();
            while ((count > level) && (pos > -1)) {
                count--;
                pos = resource.lastIndexOf('/', pos - 1);
            }
        }
        if (pos > -1) {
            // To many levels walked
            result = resource.substring(0, pos + 1);
        } else {
            // Add trailing slash
            result = (level < 0) ? "/" : resource;
        }
        return result;
    }

    static public String[] splitPath( String resource ) {
        if( StringUtils.isEmpty(resource) ) {
            return null;
        }

        if( resource.startsWith("/") ) {
            resource = resource.substring(1);
        }

        return resource.split("/");
    }

    static public void main(String[] args ) {
        String path = "/workflow/storage/file/asasas";
        System.out.println(path);
        System.out.println(getPathFromLevel(path,4));
        System.out.println(getName(getPathFromLevel(path,4)));
        // path level: /0/1/
        path = "/Workflows/";
        System.out.println(path);
        System.out.println(getParentFolder( path));
        System.out.println(getPathLevel( path));

        path = "/Workflows/EUC-27/";
        System.out.println(path);
        System.out.println(getPathLevel( path));
        System.out.println(getParentFolder( path));

    }

}
