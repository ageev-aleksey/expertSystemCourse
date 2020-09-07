package expert.productional.impl;

import expert.productional.Term;
import expert.productional.except.ExistsException;
import expert.productional.except.NotFoundException;

import java.lang.ref.WeakReference;

public class DictionaryTerm implements Term {

    WeakReference<InMemoryStorage> mDict;
    String mName;
    DictionaryTerm() {}
    // TODO(ageev) implements hash, equals
    @Override
    public void setName(String name) throws ExistsException {

        try {
            Term term = get().getTerm(mName);
            term.setName(name);
            // TODO(ageev) прехеширование? ключ хеш таблицы изменился!
        } catch (NotFoundException exp) {
            // TODO(ageev) Данная ошибка ни когда не должна произойти!
        }

    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public void setDescription(String description) {
        Term realTerm = getRealTerm();
        realTerm.setDescription(description);
    }

    @Override
    public String getDescription() {
        return getRealTerm().getDescription();
    }

    private Term getRealTerm() {
        return get().mDict.get(mName).term;
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


    private InMemoryStorage get() {
        InMemoryStorage storage = mDict.get();
        if(storage == null) {
            throw new RuntimeException("Dictionary already destroyed");
        }
        return storage;
    }
}
