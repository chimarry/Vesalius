package pro.arste.tokenserver.services;

public class TokenServiceProxy implements pro.arste.tokenserver.services.TokenService {
  private String _endpoint = null;
  private pro.arste.tokenserver.services.TokenService tokenService = null;
  
  public TokenServiceProxy() {
    _initTokenServiceProxy();
  }
  
  public TokenServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initTokenServiceProxy();
  }
  
  private void _initTokenServiceProxy() {
    try {
      tokenService = (new pro.arste.tokenserver.services.TokenServiceServiceLocator()).getTokenService();
      if (tokenService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)tokenService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)tokenService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (tokenService != null)
      ((javax.xml.rpc.Stub)tokenService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public pro.arste.tokenserver.services.TokenService getTokenService() {
    if (tokenService == null)
      _initTokenServiceProxy();
    return tokenService;
  }
  
  public java.lang.String generateToken(java.lang.String firstName, java.lang.String lastName, java.lang.String ubn) throws java.rmi.RemoteException{
    if (tokenService == null)
      _initTokenServiceProxy();
    return tokenService.generateToken(firstName, lastName, ubn);
  }
  
  
}