package org.openforis.ceo;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import java.io.File;
import java.net.URL;
import spark.servlet.SparkApplication;
import spark.template.freemarker.FreeMarkerEngine;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

public class Server implements SparkApplication {

    // Returns a FreeMarkerEngine object configured to read *.ftl
    // files from src/main/resources/template/freemarker/
    private static FreeMarkerEngine getTemplateRenderer() {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
            URL templateDirectory = Server.class.getResource("/template/freemarker");
            cfg.setDirectoryForTemplateLoading(new File(templateDirectory.toURI()));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
            // cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            return new FreeMarkerEngine(cfg);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Sets up Spark's routing table and exception handling rules
    private static void declareRoutes() {
        // Create a configured FreeMarker renderer
        FreeMarkerEngine freemarker = getTemplateRenderer();

        // FIXME: Get deploy/clientkeystore signed by a certificate authority.
        // https://docs.oracle.com/cd/E19509-01/820-3503/ggfen/index.html
        // secure("deploy/clientkeystore", "ceocert", null, null);

        // Serve static files from src/main/resources/public/
        staticFileLocation("/public");

        // Setup Routes
        get("/",                                 (req, res) -> { return freemarker.render(Views.home(req, res)); });
        get("/home",                             (req, res) -> { return freemarker.render(Views.home(req, res)); });
        get("/about",                            (req, res) -> { return freemarker.render(Views.about(req, res)); });
        get("/support",                          (req, res) -> { return freemarker.render(Views.support(req, res)); });
        get("/account",                          (req, res) -> { return freemarker.render(Views.account(req, res)); });
        post("/account",                         (req, res) -> { return freemarker.render(Views.account(Users.updateAccount(req, res), res)); });
        get("/institution",                      (req, res) -> { return freemarker.render(Views.institution(req, res)); });
        get("/dashboard",                        (req, res) -> { return freemarker.render(Views.dashboard(req, res)); });
        get("/admin",                            (req, res) -> { return freemarker.render(Views.admin(req, res)); });
        post("/admin",                           (req, res) -> { return freemarker.render(Views.admin(Projects.createNewProject(req, res), res)); });
        get("/login",                            (req, res) -> { return freemarker.render(Views.login(req, res)); });
        post("/login",                           (req, res) -> { return freemarker.render(Views.login(Users.login(req, res), res)); });
        get("/register",                         (req, res) -> { return freemarker.render(Views.register(req, res)); });
        post("/register",                        (req, res) -> { return freemarker.render(Views.register(Users.register(req, res), res)); });
        get("/password",                         (req, res) -> { return freemarker.render(Views.password(req, res)); });
        post("/password",                        (req, res) -> { return freemarker.render(Views.password(Users.requestPasswordResetKey(req, res), res)); });
        get("/password-reset",                   (req, res) -> { return freemarker.render(Views.passwordReset(req, res)); });
        post("/password-reset",                  (req, res) -> { return freemarker.render(Views.passwordReset(Users.resetPassword(req, res), res)); });
        get("/logout",                           (req, res) -> { return freemarker.render(Views.home(Users.logout(req), res)); });
        get("/get-all-projects",                 (req, res) -> { return Projects.getAllProjects(req, res); });
        post("/get-project-plots",               (req, res) -> { return Projects.getProjectPlots(req, res); });
        post("/dump-project-aggregate-data",     (req, res) -> { return Projects.dumpProjectAggregateData(req, res); });
        post("/archive-project",                 (req, res) -> { return Projects.archiveProject(req, res); });
        post("/add-user-samples",                (req, res) -> { return Projects.addUserSamples(req, res); });
        post("/flag-plot",                       (req, res) -> { return Projects.flagPlot(req, res); });
        get("/get-all-users",                    (req, res) -> { return Users.getAllUsers(req, res); });
        get("/get-all-institutions",             (req, res) -> { return Institutions.getAllInstitutions(req, res); });
        get("/geo-dash",                         (req, res) -> { return freemarker.render(Views.geodash(req, res)); });
        get("/geo-dash/id/:id",                  (req, res) -> { return GeoDash.geodashId(req, res); });
        get("/geo-dash/update/id/:id",           (req, res) -> { return GeoDash.updateDashBoardByID(req, res); });
        get("/geo-dash/createwidget/widget",     (req, res) -> { return GeoDash.createDashBoardWidgetByID(req, res); });
        get("/geo-dash/updatewidget/widget/:id", (req, res) -> { return GeoDash.updateDashBoardWidgetByID(req, res); });
        get("/geo-dash/deletewidget/widget/:id", (req, res) -> { return GeoDash.deleteDashBoardWidgetByID(req, res); });
        get("*",                                 (req, res) -> { return freemarker.render(Views.pageNotFound(req, res)); });

        // Handle Exceptions
        exception(Exception.class, (e, req, rsp) -> e.printStackTrace());
    }

    // Maven/Gradle entry point for running with embedded Jetty webserver
    public static void main(String[] args) {
        // Set the webserver port
        port(8080);

        // Set up the routing table
        declareRoutes();
    }

    // Tomcat entry point
    public void init() {
        // Set up the routing table
        declareRoutes();
    }

}
