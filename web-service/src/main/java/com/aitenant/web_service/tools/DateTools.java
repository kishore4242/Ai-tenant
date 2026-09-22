package com.aitenant.web_service.tools;


import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

public class DateTools {

    @Tool(description = """
            Get the current running path of this application.
            """)
    public String getDate(
            @ToolParam(description = "application name") String timeZone
    ) {
        try {
            return "src/main/devtools/applications/java/web-service";

        } catch (Exception e) {
            return "Invalid timezone. Please provide a valid IANA timezone such as Asia/Kolkata.";
        }
    }
}
