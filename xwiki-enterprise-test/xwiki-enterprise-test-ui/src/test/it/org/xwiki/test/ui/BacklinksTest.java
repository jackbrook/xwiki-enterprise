/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.test.ui;

import org.junit.Test;
import org.junit.Assert;
import org.xwiki.test.ui.framework.AbstractAdminAuthenticatedTest;
import org.xwiki.test.ui.framework.elements.ViewPage;

/**
 * Verify the Backlinks feature.
 *
 * @version $Id$
 * @since 3.2M1
 */
public class BacklinksTest extends AbstractAdminAuthenticatedTest
{
    @Test
    public void testBacklinksCreationSyntax10() throws Exception
    {
        testBacklinksCreation("xwiki/1.0", "#foreach ($link in $doc.getBacklinks())\n$link\n#end",
            "[backlink>Test.BacklinkTargetTest]");
    }

    @Test
    public void testBacklinksCreationSyntax20() throws Exception
    {
        testBacklinksCreation("xwiki/2.0",
            "{{velocity}}#foreach ($link in $doc.getBacklinks())\n$link\n#end{{/velocity}}",
            "[[backlink>>Test.BacklinkTargetTest]]");
    }

    private void testBacklinksCreation(String syntaxId, String backlinkListCode, String backlinkLink) throws Exception
    {
        getUtil().deletePage("Test", "BacklinkTargetTest");
        getUtil().deletePage("Test", "BacklinkSourceTest");

        // Create page listing backlinks leading to it.
        ViewPage vp = getUtil().createPage("Test", "BacklinkTargetTest", backlinkListCode, null, syntaxId);
        // No backlinks at this stage
        Assert.assertEquals("", vp.getContent());

        // Create page pointing to the page listing the backlinks.
        getUtil().createPage("Test", "BacklinkSourceTest", backlinkLink, null, syntaxId);

        vp = getUtil().gotoPage("Test", "BacklinkTargetTest");
        Assert.assertEquals("Test.BacklinkSourceTest", vp.getContent());
    }
}
