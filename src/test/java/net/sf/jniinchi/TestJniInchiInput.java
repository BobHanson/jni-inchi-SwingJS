/*
 *  JNI InChI Wrapper - A Java Native Interface Wrapper for InChI.
 *  Copyright 2006, 2007, 2008 Sam Adams <sea36 at users.sourceforge.net>
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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301 USA
 * or see <http://www.gnu.org/licenses/>.
 */
package net.sf.jniinchi;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

public class TestJniInchiInput extends TestCase {

    /*
     * Test method for 'net.sf.jniinchi.JniInchiInput.getOptions()'
     */
    @Test
    public void testGetOptions() {
        JniInchiInput input = new JniInchiInput();
        input.options = "-compress";
        Assert.assertEquals("-compress", input.getOptions());
    }
}
