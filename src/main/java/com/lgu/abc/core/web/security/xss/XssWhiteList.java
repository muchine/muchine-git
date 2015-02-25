package com.lgu.abc.core.web.security.xss;

import org.jsoup.safety.Whitelist;

public class XssWhiteList {

	private static final String[] HTMLTAG_GLOBAL_ATTRIBUTES = { "accesskey",
			"class", "contenteditable", "contextmenu", "data-*", "dir",
			"draggable", "dropzone", "hidden", "id", "lang", "spellcheck",
			"style", "tabindex", "title", "translate" };

	private static final String[] HTMLTAG_A_ATTRIBUTES = { "charset", "coords",
			"download", "href", "hreflang", "media", "name", "rel", "rev",
			"shape", "target", "type" };

	private static final String[] HTMLTAG_B_ATTRIBUTES = {};

	private static final String[] HTMLTAG_BLOCKQUOTE_ATTRIBUTES = { "cite" };

	private static final String[] HTMLTAG_BR_ATTRIBUTES = {};

	private static final String[] HTMLTAG_CAPTION_ATTRIBUTES = { "align" };

	private static final String[] HTMLTAG_CITE_ATTRIBUTES = {};

	private static final String[] HTMLTAG_CODE_ATTRIBUTES = {};

	private static final String[] HTMLTAG_COL_ATTRIBUTES = { "align", "char",
			"charoff", "span", "valign", "width" };

	private static final String[] HTMLTAG_COLGROUP_ATTRIBUTES = { "align",
			"char", "charoff", "span", "valign", "width" };

	private static final String[] HTMLTAG_DD_ATTRIBUTES = {};

	private static final String[] HTMLTAG_DIV_ATTRIBUTES = { "align" };

	private static final String[] HTMLTAG_DL_ATTRIBUTES = {};

	private static final String[] HTMLTAG_DT_ATTRIBUTES = {};

	private static final String[] HTMLTAG_EM_ATTRIBUTES = {};

	private static final String[] HTMLTAG_H1_ATTRIBUTES = { "align" };

	private static final String[] HTMLTAG_H2_ATTRIBUTES = { "align" };

	private static final String[] HTMLTAG_H3_ATTRIBUTES = { "align" };

	private static final String[] HTMLTAG_H4_ATTRIBUTES = { "align" };

	private static final String[] HTMLTAG_H5_ATTRIBUTES = { "align" };

	private static final String[] HTMLTAG_H6_ATTRIBUTES = { "align" };

	private static final String[] HTMLTAG_I_ATTRIBUTES = {};

	private static final String[] HTMLTAG_IMG_ATTRIBUTES = { "align", "alt",
			"border", "crossorigin", "height", "hspace", "ismap", "longdesc",
			"src", "usemap", "vspace", "width" };

	private static final String[] HTMLTAG_LI_ATTRIBUTES = { "type", "value" };

	private static final String[] HTMLTAG_OL_ATTRIBUTES = { "compact",
			"reversed", "start", "type" };

	private static final String[] HTMLTAG_P_ATTRIBUTES = { "align" };

	private static final String[] HTMLTAG_PRE_ATTRIBUTES = { "width" };

	private static final String[] HTMLTAG_Q_ATTRIBUTES = { "cite" };

	private static final String[] HTMLTAG_SMALL_ATTRIBUTES = {};

	private static final String[] HTMLTAG_STRIKE_ATTRIBUTES = { "class", "dir",
			"id", "lang", "style", "title" };

	private static final String[] HTMLTAG_STRONG_ATTRIBUTES = {};

	private static final String[] HTMLTAG_SUB_ATTRIBUTES = {};

	private static final String[] HTMLTAG_SUP_ATTRIBUTES = {};

	private static final String[] HTMLTAG_TABLE_ATTRIBUTES = { "align",
			"bgcolor", "border", "cellpadding", "cellspacing", "frame",
			"rules", "sortable", "summary", "width" };

	private static final String[] HTMLTAG_TBODY_ATTRIBUTES = { "align", "char",
			"charoff", "valign" };

	private static final String[] HTMLTAG_TD_ATTRIBUTES = { "abbr", "align",
			"axis", "bgcolor", "char", "charoff", "colspan", "headers",
			"height", "nowrap", "rowspan", "scope", "valign", "width" };

	private static final String[] HTMLTAG_TFOOT_ATTRIBUTES = { "align", "char",
			"charoff", "valign" };

	private static final String[] HTMLTAG_TH_ATTRIBUTES = { "abbr", "align",
			"axis", "bgcolor", "char", "charoff", "colspan", "headers",
			"height", "nowrap", "rowspan", "scope", "sorted", "valign", "width" };

	private static final String[] HTMLTAG_THEAD_ATTRIBUTES = { "align", "char",
			"charoff", "valign" };

	private static final String[] HTMLTAG_TR_ATTRIBUTES = { "align", "bgcolor",
			"char", "charoff", "valign" };

	private static final String[] HTMLTAG_U_ATTRIBUTES = {};

	private static final String[] HTMLTAG_UL_ATTRIBUTES = { "compact", "type" };

	private static final String[] HTMLTAG_EMBED_ATTRIBUTES = { "height", "src",
			"type", "width" };

	private static final String[] HTMLTAG_FONT_ATTRIBUTES = { "color", "face",
			"size", "class", "dir", "id", "lang", "style", "title" };

	private static final String[] HTMLTAG_HR_ATTRIBUTES = { "align", "noshade",
			"size", "width" };

	private static final String[] HTMLTAG_INPUT_ATTRIBUTES = { "accept",
			"align", "alt", "autocomplete", "autofocus", "checked", "disabled",
			"form", "formaction", "formenctype", "formmethod",
			"formnovalidate", "formtarget", "height", "list", "max",
			"maxlength", "min", "multiple", "name", "pattern", "placeholder",
			"readonly", "required", "size", "src", "step", "type", "value",
			"width" };

	private static final String[] HTMLTAG_OBJECT_ATTRIBUTES = { "align",
			"archive", "border", "classid", "codebase", "codetype", "data",
			"declare", "form", "height", "hspace", "name", "standby", "type",
			"usemap", "vspace", "width" };

	private static final String[] HTMLTAG_PARAM_ATTRIBUTES = { "name", "type",
			"value", "valuetype" };

	private static final String[] HTMLTAG_SELECT_ATTRIBUTES = { "autofocus",
			"disabled", "form", "multiple", "name", "required", "size" };

	private static final String[] HTMLTAG_SPAN_ATTRIBUTES = {};

	private static final String[] HTMLTAG_TEXTAREA_ATTRIBUTES = { "autofocus",
			"cols", "disabled", "form", "maxlength", "name", "placeholder",
			"readonly", "required", "rows", "wrap" };

	private static final String[] HTMLTAG_MAP_ATTRIBUTES = { "name"};
	
	private static final String[] HTMLTAG_AREA_ATTRIBUTES = { "alt", "coords", "download",
		"href", "hreflang", "media", "nohref", "rel", "shape", "target"};
	
	public static final Whitelist supported() {
		return new Whitelist()
				// jsoup relaxed Tag 리스트
				.addTags("a", "b", "blockquote", "br", "caption", "cite",
						"code", "col", "colgroup", "dd", "div", "dl", "dt",
						"em", "h1", "h2", "h3", "h4", "h5", "h6", "i", "img",
						"li", "ol", "p", "pre", "q", "small", "strike",
						"strong", "sub", "sup", "table", "tbody", "td",
						"tfoot", "th", "thead", "tr", "u", "ul", "map", "area")
				// jsoup relaxed Tag 속성 
				.addAttributes("a", addGlobalAttributes(HTMLTAG_A_ATTRIBUTES))
				.addAttributes("b", addGlobalAttributes(HTMLTAG_B_ATTRIBUTES))
				.addAttributes("blockquote", addGlobalAttributes(HTMLTAG_BLOCKQUOTE_ATTRIBUTES))
				.addAttributes("br", addGlobalAttributes(HTMLTAG_BR_ATTRIBUTES))
				.addAttributes("caption", addGlobalAttributes(HTMLTAG_CAPTION_ATTRIBUTES))
				.addAttributes("cite", addGlobalAttributes(HTMLTAG_CITE_ATTRIBUTES))
				.addAttributes("code", addGlobalAttributes(HTMLTAG_CODE_ATTRIBUTES))
				.addAttributes("col", addGlobalAttributes(HTMLTAG_COL_ATTRIBUTES))
				.addAttributes("colgroup", addGlobalAttributes(HTMLTAG_COLGROUP_ATTRIBUTES))
				.addAttributes("dd", addGlobalAttributes(HTMLTAG_DD_ATTRIBUTES))
				.addAttributes("div", addGlobalAttributes(HTMLTAG_DIV_ATTRIBUTES))
				.addAttributes("dl", addGlobalAttributes(HTMLTAG_DL_ATTRIBUTES))
				.addAttributes("dt", addGlobalAttributes(HTMLTAG_DT_ATTRIBUTES))
				.addAttributes("em", addGlobalAttributes(HTMLTAG_EM_ATTRIBUTES))
				.addAttributes("h1", addGlobalAttributes(HTMLTAG_H1_ATTRIBUTES))
				.addAttributes("h2", addGlobalAttributes(HTMLTAG_H2_ATTRIBUTES))
				.addAttributes("h3", addGlobalAttributes(HTMLTAG_H3_ATTRIBUTES))
				.addAttributes("h4", addGlobalAttributes(HTMLTAG_H4_ATTRIBUTES))
				.addAttributes("h5", addGlobalAttributes(HTMLTAG_H5_ATTRIBUTES))
				.addAttributes("h6", addGlobalAttributes(HTMLTAG_H6_ATTRIBUTES))
				.addAttributes("i", addGlobalAttributes(HTMLTAG_I_ATTRIBUTES))
				.addAttributes("img", addGlobalAttributes(HTMLTAG_IMG_ATTRIBUTES))
				.addAttributes("li", addGlobalAttributes(HTMLTAG_LI_ATTRIBUTES))
				.addAttributes("ol", addGlobalAttributes(HTMLTAG_OL_ATTRIBUTES))
				.addAttributes("p", addGlobalAttributes(HTMLTAG_P_ATTRIBUTES))
				.addAttributes("pre", addGlobalAttributes(HTMLTAG_PRE_ATTRIBUTES))
				.addAttributes("q", addGlobalAttributes(HTMLTAG_Q_ATTRIBUTES))
				.addAttributes("small", addGlobalAttributes(HTMLTAG_SMALL_ATTRIBUTES))
				.addAttributes("strike", HTMLTAG_STRIKE_ATTRIBUTES)
				.addAttributes("strong", addGlobalAttributes(HTMLTAG_STRONG_ATTRIBUTES))
				.addAttributes("sub", addGlobalAttributes(HTMLTAG_SUB_ATTRIBUTES))
				.addAttributes("sup", addGlobalAttributes(HTMLTAG_SUP_ATTRIBUTES))
				.addAttributes("table", addGlobalAttributes(HTMLTAG_TABLE_ATTRIBUTES))
				.addAttributes("tbody", addGlobalAttributes(HTMLTAG_TBODY_ATTRIBUTES))
				.addAttributes("td", addGlobalAttributes(HTMLTAG_TD_ATTRIBUTES))
				.addAttributes("tfoot", addGlobalAttributes(HTMLTAG_TFOOT_ATTRIBUTES))
				.addAttributes("th", addGlobalAttributes(HTMLTAG_TH_ATTRIBUTES))
				.addAttributes("thead", addGlobalAttributes(HTMLTAG_THEAD_ATTRIBUTES))
				.addAttributes("tr", addGlobalAttributes(HTMLTAG_TR_ATTRIBUTES))
				.addAttributes("u", addGlobalAttributes(HTMLTAG_U_ATTRIBUTES))
				.addAttributes("ul", addGlobalAttributes(HTMLTAG_UL_ATTRIBUTES))
				// jsoup relaxed Tag 프로토콜
				// .addProtocols("a", "href", "ftp", "http", "https", "mailto")
				// .addProtocols("blockquote", "cite", "http", "https")
				// .addProtocols("img", "src", "http", "https")
				// .addProtocols("q", "cite", "http", "https")
				// ckeditor 추가 리스트
				.addTags("embed", "font", "hr", "input", "object", "param", "select", "span", "textarea")
				// ckeditor Tag 속성 
				.addAttributes("embed", addGlobalAttributes(HTMLTAG_EMBED_ATTRIBUTES))
				.addAttributes("font", HTMLTAG_FONT_ATTRIBUTES)
				.addAttributes("hr", addGlobalAttributes(HTMLTAG_HR_ATTRIBUTES))
				.addAttributes("input", addGlobalAttributes(HTMLTAG_INPUT_ATTRIBUTES))
				.addAttributes("object", addGlobalAttributes(HTMLTAG_OBJECT_ATTRIBUTES))
				.addAttributes("param", addGlobalAttributes(HTMLTAG_PARAM_ATTRIBUTES))
				.addAttributes("select", addGlobalAttributes(HTMLTAG_SELECT_ATTRIBUTES))
				.addAttributes("span", addGlobalAttributes(HTMLTAG_SPAN_ATTRIBUTES))
				.addAttributes("textarea", addGlobalAttributes(HTMLTAG_TEXTAREA_ATTRIBUTES))
				.addAttributes("map", addGlobalAttributes(HTMLTAG_MAP_ATTRIBUTES))
				.addAttributes("area", addGlobalAttributes(HTMLTAG_AREA_ATTRIBUTES));
	}

	// html tag 전역 속성 추가
	private static String[] addGlobalAttributes(String[] basicAttributes) {
		String[] globalAttributes = HTMLTAG_GLOBAL_ATTRIBUTES;
		String[] totalAttributes = new String[globalAttributes.length + basicAttributes.length];
		System.arraycopy(globalAttributes, 0, totalAttributes, 0, globalAttributes.length);
		System.arraycopy(basicAttributes, 0, totalAttributes, globalAttributes.length, basicAttributes.length);
		return totalAttributes;
	}
	
}
