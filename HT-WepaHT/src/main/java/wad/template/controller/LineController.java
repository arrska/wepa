package wad.template.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wad.template.domain.Line;
import wad.template.service.TimetableService;

@Controller
public class LineController {
    @Autowired
    private TimetableService timetableService;
    
    @RequestMapping(value = "line")
    public String lineInfo(@RequestParam(value = "linecode") String lineCode, Model model) {
        System.out.println(lineCode);
        Line line = timetableService.getLine(lineCode);
        model.addAttribute("line", line);
        
        return "line/lineinfo";
    }
}
