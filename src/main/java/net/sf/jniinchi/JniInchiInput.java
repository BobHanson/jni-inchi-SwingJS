/*
 * JNI InChI Wrapper - A Java Native Interface Wrapper for InChI.
 * Copyright (C) 2006-2007  Sam Adams
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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA
 */
package net.sf.jniinchi;

import java.util.List;

/**
 * Encapsulates structure input for InChI generation.
 * @author Sam Adams
 */
public class JniInchiInput extends JniInchiStructure {

    /**
     * Options string,
     */
    protected String options;

    /**
     * Constructor.
     * @throws JniInchiException
     */
    public JniInchiInput() {
        this.options = "";
    }

    /**
     * Constructor.
     * @param opts    Options string.
     * @throws JniInchiException
     */
    public JniInchiInput(final String opts) throws JniInchiException {
        this.options = JniInchiWrapper.checkOptions(opts);
    }

    /**
     * Constructor.
     * @param opts    List of options.
     * @throws JniInchiException
     */
    public JniInchiInput(List opts) throws JniInchiException {
        this.options = JniInchiWrapper.checkOptions(opts);
    }

    /**
     * Constructor.
     * @throws JniInchiException
     */
    public JniInchiInput(JniInchiStructure struct) {
        this();
        setStructure(struct);
    }

    /**
     * Constructor.
     * @throws JniInchiException
     */
    public JniInchiInput(JniInchiStructure struct, String opts) throws JniInchiException {
        this(opts);
        setStructure(struct);
    }

    /**
     * Returns options string.
     * @return
     */
    public String getOptions() {
        return options;
    }
}
