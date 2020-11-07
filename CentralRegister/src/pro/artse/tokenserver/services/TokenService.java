/**
 * TokenService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pro.artse.tokenserver.services;

public interface TokenService extends java.rmi.Remote {
    public java.lang.String getAll() throws java.rmi.RemoteException;
    public java.lang.String isValidToken(java.lang.String token) throws java.rmi.RemoteException;
    public java.lang.String generateToken(java.lang.String firstName, java.lang.String lastName, java.lang.String ubn) throws java.rmi.RemoteException;
}
