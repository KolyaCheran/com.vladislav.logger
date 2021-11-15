package com.vladislav.logger.controllers.view;

import com.vladislav.logger.dao.RunDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/builds")
public class BuildsController {

    private final RunDAO runDAO;

    @Autowired
    public BuildsController(RunDAO runDAO) {
        this.runDAO = runDAO;
    }

    @GetMapping()
    public String buildsPage(@RequestParam("buildname") String buildName,
                           @RequestParam("day") int day,
                           @RequestParam("month") int month,
                           @RequestParam("year") int year,
                           Model model) {
        String titleText = "Runs for " + day + "." + month + "." + year;
        Set<String> runs;
        if (buildName != null && !buildName.isEmpty()) {
            runs = runDAO.getRunForDateAndBuildName(day, month, year, buildName);
            titleText += " and build name " + buildName;
        } else {
            runs = runDAO.getRunForDate(day, month, year);
        }
        model.addAttribute("runs", runs);
        model.addAttribute("day", day);
        model.addAttribute("month", month);
        model.addAttribute("year", year);
        model.addAttribute("title", titleText);
        return "vladislav/builds";
    }

    private Map<String, String> convertIdsToString(Map<String, List<Integer>> runs) {
        Map<String, String> resultMap = new HashMap<>();
        for(Map.Entry<String, List<Integer>> run : runs.entrySet()){
            StringBuilder resultIds = null;
            for(Integer id : run.getValue()){
                if (resultIds == null){
                    resultIds = new StringBuilder(id.toString());
                } else {
                    resultIds.append(",").append(id);
                }
            }
            resultMap.put(run.getKey(), resultIds.toString());
        }
        return resultMap;
    }
}
