package Utlities;

import java.util.HashMap;

public class IdentifiedTokenHolder {

    private HashMap<String, String> tokenMapping;

    public IdentifiedTokenHolder() {
        this.tokenMapping = new HashMap<String, String>();
    }

    public void addToken(String key, String text) {
        this.tokenMapping.put(key, text);
    }

    public void removeToken(String key) {
        if(this.tokenMapping.containsKey(key)) {
            this.tokenMapping.remove(key);
        }
    }

    public String getToken(String key) {
        if(this.tokenMapping.containsKey(key)) {
            return this.tokenMapping.get(key);
        }
        else {
//            Log.e(TAG, key +" not found in list of tokens.");
            System.err.println( key +" not found in list of tokens."); //TODO: Change to IDE
            return null;
        }
    }

    public int getTokenListLength() {
        return this.tokenMapping.size();
    }


    /*
     * Returns true if all of the specified keys has been found
     */
    public boolean containsTokens(String...keys) {
        for(int i = 0; i < keys.length; i++) {
            if(!this.tokenMapping.containsKey(keys[i])) {
                return false;
            }
        }

        return true;
    }

    public void clearTokens() {
        this.tokenMapping.clear();
    }
}