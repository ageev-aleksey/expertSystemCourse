package expert.productional.impl;

import expert.productional.Term;
import expert.productional.except.ExistsException;

public class UserTerm implements Term {

    private String mName;
    private String mDescription;

    public UserTerm(String name, String description) {
        mName = name;
        mDescription = description;
    }

    @Override
    public void setName(String name) throws ExistsException {
        mName = name;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public void setDescription(String description) {
        mDescription = description;
    }

    @Override
    public String getDescription() {
        return mDescription;
    }

    public int hashCode() {
        return mName.hashCode();
    }

    public boolean equals(Object obj) {
        if(mName == null) {
            return obj == null;
        }
        if(!(obj instanceof Term)) return false;
        return mName.equals(((Term) obj).getName());
    }
}
