package com.lgu.abc.core.web.editor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lgu.abc.core.base.controller.AbstractHomeController;

@Controller("editorHomeController")
@RequestMapping("/common/editor")
public class EditorHomeController extends AbstractHomeController {

	@RequestMapping(method = RequestMethod.GET, value = "/daum/guide")
	public String guideDaum(Model model) throws Exception {
		return view("/daum/guide.jstl");
	}

	@RequestMapping(method = RequestMethod.GET, value = "/naver/guide")
	public String guideNaver(Model model) throws Exception {
		return view("/naver/guide.jstl");
	}

	@RequestMapping(method = RequestMethod.GET, value = "/help/guide")
	public String guideHelp(Model model) throws Exception {
		return view("/help/guide.jstl");
	}

}