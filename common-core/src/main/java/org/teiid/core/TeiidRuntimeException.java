/*
 * JBoss, Home of Professional Open Source.
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership.  Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */

package org.teiid.core;



/**
 * A generic runtime exception which contains a reference to another exception
 * and which represents a condition that should never occur during runtime.  This
 * class can be used to maintain a linked list of exceptions. <p>
 *
 * Subclasses of this exception typically only need to implement whatever
 * constructors they need. <p>
 */
public class TeiidRuntimeException extends RuntimeException {
    public static final long serialVersionUID = -4035276728007979320L;
    
    public static final String CAUSED_BY_STRING = CorePlugin.Util.getString("RuntimeException.Caused_by"); //$NON-NLS-1$
    
    //############################################################################################################################
    //# Variables                                                                                                                #
    //############################################################################################################################

    /** An error code. */
    private String code;

    //############################################################################################################################
    //# Constructors                                                                                                             #
    //############################################################################################################################

    /**
     * Construct a default instance of this class.
     */
    public TeiidRuntimeException() {
    }

    /**
     * Construct an instance with the specified error message.  If the message is actually a key, the actual message will be
     * retrieved from a resource bundle using the key, the specified parameters will be substituted for placeholders within the
     * message, and the code will be set to the key.
     * @param message The error message or a resource bundle key
     */
    public TeiidRuntimeException(final String message) {
        super(message);
    }

    TeiidRuntimeException(final String code, final String message) {
        super(message);
        // The following setCode call should be executed after setting the message 
        setCode(code);
    }
    
    public TeiidRuntimeException(BundleUtil.Event code, final String message) {
        super(message);
        // The following setCode call should be executed after setting the message 
        setCode(code.toString());
    }    
    
    public TeiidRuntimeException(BundleUtil.Event code, final Throwable t) {
        super(t);
        // The following setCode call should be executed after setting the message 
        setCode(code.toString());
    }    
    
    /**
     * Construct an instance with a linked exception specified.  If the exception is a {@link TeiidException} or a
     * TeoodRuntimeException, then the code will be set to the exception's code.
     * @param e An exception to chain to this exception
     */
    public TeiidRuntimeException(final Throwable e) {
        super(( e instanceof java.lang.reflect.InvocationTargetException )
                ? ((java.lang.reflect.InvocationTargetException)e).getTargetException().getMessage()
                        : (e == null ? null : e.getMessage()), e);
        setCode(TeiidException.getCode(e));
    }

    /**
     * Construct an instance with the linked exception, error code, and error message specified. If the specified
     * exception is a {@link TeiidException} or a MetaMatrixRuntimeException, the code will
     * be set to the exception's code.
     * @param e       The exception to chain to this exception
     * @param code    The error code
     * @param message The error message
     */
    public TeiidRuntimeException(BundleUtil.Event event, final Throwable e, final String message) {
        super(message, e);
        // Overwrite code set in other ctor from exception.
        setCode(event.toString());
    }


    //############################################################################################################################
    //# Methods                                                                                                                  #
    //############################################################################################################################

    /**
     * Get the error code.
     *
     * @return The error code 
     */
    public String getCode() {
        return this.code;
    }
    
    private void setCode( String code ) {
        this.code = code;
    }

	public String getMessage() {
		String message = super.getMessage();
		if (code == null || code.length() == 0 || message.startsWith(code)) {
			return message;
		}
		return code+" "+message; //$NON-NLS-1$
	} 

}
