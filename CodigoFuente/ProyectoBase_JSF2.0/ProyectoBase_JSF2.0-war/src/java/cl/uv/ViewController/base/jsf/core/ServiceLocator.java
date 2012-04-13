package cl.uv.ViewController.base.jsf.core;

import cl.uv.ViewController.base.utils.Resources;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

public class ServiceLocator {

    private Map<String, Object> dataSourceCache;
    private InitialContext ic;
    private static ServiceLocator me;
    private static final String jndiLocalLookUp = Resources.getValue("basicWebParam_path", "lookUpLocalJndi");

    static {
        try {
            me = new ServiceLocator();
        } catch (Exception se) {
            se.printStackTrace(System.err);
        }
    }

    private ServiceLocator() {
        try {
            ic = new InitialContext();
            dataSourceCache = Collections.synchronizedMap(new HashMap<String, Object>());
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
    }

    public static final ServiceLocator getInstance() {
        return me;
    }

    @Override
    public final Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public final Object getLocalInterface(String jndiHomeName) throws ServiceLocatorException {
        Object ejbLocal = null;
        try {
            if (dataSourceCache.containsKey(jndiHomeName)) {
                ejbLocal = (Object) dataSourceCache.get(jndiHomeName);
            } else {
                ejbLocal = (Object) ic.lookup(jndiLocalLookUp + jndiHomeName);
                dataSourceCache.put(jndiHomeName, ejbLocal);
            }
        } catch (NamingException ex) {
            throw new ServiceLocatorException("JNDI DataSource incorrecto", ex.getCause());
        } catch (Exception e) {
            throw new ServiceLocatorException(e.getCause());
        }
        return ejbLocal;
    }

    public final Object getRemoteInterface(String jndiHomeName, Class className) throws ServiceLocatorException {
        Object ejbRemote = null;
        try {
            if (dataSourceCache.containsKey(jndiHomeName)) {
                ejbRemote = (Object) dataSourceCache.get(jndiHomeName);
            } else {
                Object objref = ic.lookup(jndiHomeName);
                ejbRemote = (Object) PortableRemoteObject.narrow(objref, className);
                dataSourceCache.put(jndiHomeName, ejbRemote);
            }
        } catch (NamingException ex) {
            throw new ServiceLocatorException("JNDI DataSource incorrecto", ex.getCause());
        } catch (Exception e) {
            throw new ServiceLocatorException(e.getCause());
        }
        return ejbRemote;
    }

    public final Object getDataSource(String jndiHomeName) throws ServiceLocatorException {
        Object dataSource = null;
        try {
            if (dataSourceCache.containsKey(jndiHomeName)) {
                dataSource = (Object) dataSourceCache.get(jndiHomeName);
            } else {
                dataSource = (Object) ic.lookup(jndiHomeName);
                dataSourceCache.put(jndiHomeName, dataSource);
            }
        } catch (NamingException ex) {
            throw new ServiceLocatorException("JNDI DataSource incorrecto", ex.getCause());
        } catch (Exception e) {
            throw new ServiceLocatorException(e.getCause());
        }
        return dataSource;
    }
}
