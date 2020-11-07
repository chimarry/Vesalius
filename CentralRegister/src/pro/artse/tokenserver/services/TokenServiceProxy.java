package pro.artse.tokenserver.services;

public class TokenServiceProxy implements pro.artse.tokenserver.services.TokenService {
  private String _endpoint = null;
  private pro.artse.tokenserver.services.TokenService tokenService = null;
  
  public TokenServiceProxy() {
    _initTokenServiceProxy();
  }
  
  public TokenServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initTokenServiceProxy();
  }
  
  private void _initTokenServiceProxy() {
    try {
      tokenService = (new pro.artse.tokenserver.services.TokenServiceServiceLocator()).getTokenService();
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
  
  public pro.artse.tokenserver.services.TokenService getTokenService() {
    if (tokenService == null)
      _initTokenServiceProxy();
    return tokenService;
  }
  
  public java.lang.String getAll() throws java.rmi.RemoteException{
    if (tokenService == null)
      _initTokenServiceProxy();
    return tokenService.getAll();
  }
  
  public java.lang.String isValidToken(java.lang.String token) throws java.rmi.RemoteException{
    if (tokenService == null)
      _initTokenServiceProxy();
    return tokenService.isValidToken(token);
  }
  
  public java.lang.String generateToken(java.lang.String firstName, java.lang.String lastName, java.lang.String ubn) throws java.rmi.RemoteException{
    if (tokenService == null)
      _initTokenServiceProxy();
    return tokenService.generateToken(firstName, lastName, ubn);
  }
  
  
}