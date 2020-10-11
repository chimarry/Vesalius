/**
 * TokenServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pro.arste.tokenserver.services;

public class TokenServiceServiceLocator extends org.apache.axis.client.Service implements pro.arste.tokenserver.services.TokenServiceService {

    public TokenServiceServiceLocator() {
    }


    public TokenServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public TokenServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for TokenService
    private java.lang.String TokenService_address = "http://localhost:8080/TokenServer/services/TokenService";

    public java.lang.String getTokenServiceAddress() {
        return TokenService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String TokenServiceWSDDServiceName = "TokenService";

    public java.lang.String getTokenServiceWSDDServiceName() {
        return TokenServiceWSDDServiceName;
    }

    public void setTokenServiceWSDDServiceName(java.lang.String name) {
        TokenServiceWSDDServiceName = name;
    }

    public pro.arste.tokenserver.services.TokenService getTokenService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(TokenService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getTokenService(endpoint);
    }

    public pro.arste.tokenserver.services.TokenService getTokenService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            pro.arste.tokenserver.services.TokenServiceSoapBindingStub _stub = new pro.arste.tokenserver.services.TokenServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getTokenServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setTokenServiceEndpointAddress(java.lang.String address) {
        TokenService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (pro.arste.tokenserver.services.TokenService.class.isAssignableFrom(serviceEndpointInterface)) {
                pro.arste.tokenserver.services.TokenServiceSoapBindingStub _stub = new pro.arste.tokenserver.services.TokenServiceSoapBindingStub(new java.net.URL(TokenService_address), this);
                _stub.setPortName(getTokenServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("TokenService".equals(inputPortName)) {
            return getTokenService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://services.tokenserver.arste.pro", "TokenServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://services.tokenserver.arste.pro", "TokenService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("TokenService".equals(portName)) {
            setTokenServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
