package com.nativenote.parserss.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Text;

/**
 * Created by IMTIAZ on 3/5/17.
 */

public class Link {
    @Attribute(required = false)
    public String href;

    @Attribute(required = false)
    public String rel;

    @Attribute(name = "type", required = false)
    public String contentType;

    @Text(required = false)
    public String link;
}
