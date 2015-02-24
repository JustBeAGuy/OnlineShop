package com.epam.kiev.onlineshop.command.adminmenu;

import com.epam.kiev.onlineshop.dao.factory.DAOFactory;
import com.epam.kiev.onlineshop.entity.Good;
import com.epam.kiev.onlineshop.manager.ConfigurationManager;
import com.epam.kiev.onlineshop.manager.MessageManager;
import com.epam.kiev.onlineshop.support.Message;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Администратор on 1/27/15.
 */
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
        maxFileSize=1024*1024*10,      // 10MB
        maxRequestSize=1024*1024*50)    //50MB
public class AdminCatalog implements IAdminMenu {
    private int itemsPerPage = 2;
    private static final int FIRST_PAGE = 1;
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        ConfigurationManager configurationManager = ConfigurationManager.getInstance();
        String sortBy = "id"; // sorting by
        String cend = "ASC";
        String searchCol = "id"; // searching col
        String searchVal = "";
        itemsPerPage =  (Integer) req.getSession().getAttribute("itemsPerPage");
        //Start Add new and Update
        Good good = new Good();
        // parameter "admin_catalog_upload" can be an ID of good, or NEW;
        if(req.getParameter("admin_catalog_upload") != null) {
            //when new, insert good to DB
            if(req.getParameter("admin_catalog_upload").equals("new")) {
                if(setGood(req, good)) {
                    insertGood(good);
                }
            } else if (req.getParameter("admin_catalog_upload").matches("[0-9]+")) {
                if(req.getParameter("admin_catalog_delete") == null) {
                    if(setGood(req, good)) {
                        good.setId(req.getParameter("admin_catalog_upload"));
                        // Setting Img Path
                        if(req.getParameter("admin_catalog_img") != null) {
                            good.setImg(req.getParameter("admin_catalog_img"));
                        } else {
                            good.setImg("false");
                        }
                        //Updating without Img
                        updateGood(good);
                        Message.notify(req, MessageManager.ADDED_SUCC, MessageManager.TYPE_SUCC);
                    }
                } else {
                    deleteGood(req.getParameter("admin_catalog_upload"));
                    Message.notify(req, MessageManager.REMOVED_SUCC, MessageManager.TYPE_SUCC);
                }
            }
            //Uploading image if good != null
            if(good != null) {
                if(UploadFiles(req, getPath(req,good.getId()), good) == false) {
                    Logger.getLogger(AdminCatalog.class).info("File: is not uploaded");
                } else {
                    updateGood(good);
                }
            }
        }
        ///End Add new and Update

        //reset Attributes when refresh page
        if(req.getParameter("reset") != null) {
            resetSearchAttributes(req, sortBy, cend, searchCol, searchVal);
        }

        //Searching
        if(req.getParameter("admin_catalog_searchCol") != null) {
            req.getSession().setAttribute("admin_catalog_searchCol", req.getParameter("admin_catalog_searchCol"));
            req.getSession().setAttribute("admin_catalog_searchVal", req.getParameter("admin_catalog_searchVal"));
            //Set number of pages
            req.getSession().setAttribute("admin_catalog_number_pages", getNumberOfPages(
                    req.getParameter("admin_catalog_searchCol"),
                    req.getParameter("admin_catalog_searchVal")));
            //Set current page
            req.getSession().setAttribute("admin_catalog_curr_page", FIRST_PAGE);
        }
        searchCol = (String) req.getSession().getAttribute("admin_catalog_searchCol");
        searchVal = (String) req.getSession().getAttribute("admin_catalog_searchVal");

        //check for income param and set it
        getSortingParam(req);
        sortBy = (String) req.getSession().getAttribute("admin_catalog_sorting_sortby");
        cend = (String) req.getSession().getAttribute("admin_catalog_sorting_cend");

        //Setting current page
        if (req.getParameter("admin_catalog_curr_page") != null) {
            try {
                int currPage = Integer.parseInt(req.getParameter("admin_catalog_curr_page"));
                req.getSession().setAttribute("admin_catalog_curr_page", currPage);
            } catch (ClassCastException e) {
                Logger.getLogger(AdminCatalog.class).info(e.getMessage());
            }
        }
        int page = (Integer) req.getSession().getAttribute("admin_catalog_curr_page");
        // The first Good number in the page
        int getFirstGoodInPage = (page-1) * itemsPerPage;

        ///setting catalog attribute
        req.getSession().setAttribute("admin_catalog" ,
                getCatalog(sortBy, getFirstGoodInPage, itemsPerPage, cend, searchCol, searchVal));

        req.getSession().setAttribute("admin_content" ,
                configurationManager.getProperty(configurationManager.ADMIN_CATALOG));
    }

    private void deleteGood(String goodId) {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
        factory.getGoodDAO().deleteGood(Integer.parseInt(goodId));
    }

    private void updateGood(Good good) {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
        factory.getGoodDAO().updateGood(good);
    }

    private boolean setGood(HttpServletRequest req, Good good) {
        boolean res = false;
        try {
            good.setCategory(req.getParameter("admin_catalog_category"));
            good.setName(req.getParameter("admin_catalog_name"));
            good.setPrice(req.getParameter("admin_catalog_price"));
            good.setDescription(req.getParameter("admin_catalog_description"));
            good.setAvailability(req.getParameter("admin_catalog_availability"));
            res = true;
        } catch (NullPointerException e) {
            Logger.getLogger(AdminCatalog.class).info(e);
            good = null;
        }
        return res;
    }

    private boolean UploadFiles(HttpServletRequest req, String savePath, Good good) {
        boolean res = false;
        try {
            for(Part part : req.getParts()) {
                String fileName = part.getSubmittedFileName();
                if(fileName == null) {
                    continue;
                }
                part.write(savePath + File.separator + fileName);
                //setting good Img Path
                good.setImg(good.getId() + "/" + fileName);

                Logger.getLogger(AdminCatalog.class).info("File: " + fileName + " is uploaded");
            }
            res = true;
        } catch (IOException e) {
            Logger.getLogger(AdminCatalog.class).info(e);
        } catch (ServletException e) {
            Logger.getLogger(AdminCatalog.class).info(e);
        }

        return res;
    }

    private String getPath(HttpServletRequest req, String goodId) {
        // gets absolute path of the web application
        String appPath = req.getServletContext().getRealPath("");
        // path to the IMGS
        String imgsPath = ConfigurationManager.getInstance().getProperty(ConfigurationManager.IMG_GOOD);
        // constructs path of the directory to save uploaded file
        String savePath = appPath + File.separator + imgsPath + File.separator + goodId;
        //Creating the new Directory, if it doesn't exists
        createDir(savePath);

        return savePath;
    }

    private boolean getSortingParam(HttpServletRequest req) {
        Boolean res = false;
        //Getting parameters to SORTING
        if(req.getParameter("admin_catalog_sorting") != null) {
            String[] sortParam = req.getParameter("admin_catalog_sorting").split("_");
            String cend = "ASC";
            if(sortParam[0].equals("de")) {
                cend = "DESC";
            }
            req.getSession().setAttribute("admin_catalog_sorting_cend", cend);
            req.getSession().setAttribute("admin_catalog_sorting_sortby", sortParam[1]);
            res = true;
        }
        return res;
    }

    private void resetSearchAttributes(HttpServletRequest req, String sortBy, String cend, String searchCol, String searchVal) {
        //Set Initial sorting , if we havent needed attribute
        req.getSession().setAttribute("admin_catalog_sorting_sortby", sortBy);
        req.getSession().setAttribute("admin_catalog_sorting_cend", cend);
        //Set number of pages
        req.getSession().setAttribute("admin_catalog_number_pages", getNumberOfPages(searchCol, searchVal));
        //Set current page
        req.getSession().setAttribute("admin_catalog_curr_page", FIRST_PAGE);
        //set without search
        req.getSession().setAttribute("admin_catalog_searchCol", searchCol);
        req.getSession().setAttribute("admin_catalog_searchVal", searchVal);
    }

    private int getNumberOfPages(String searchCol, String searchVal) {
        int res = 0;
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
        int numOfGoods = factory.getGoodDAO().getNumberOfGoods(searchCol, searchVal);
        res = numOfGoods / itemsPerPage;
        if((numOfGoods % itemsPerPage) != 0) {
            res++;
        };
        return res;
    }

    private void createDir(String savePath) {
        // creates the save directory if it does not exists
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
    }

    private void insertGood(Good good) {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
        factory.getGoodDAO().insertGood(good);
    }

    private ArrayList<Good> getCatalog(String sortBy, int from, int number, String cend, String searchCol, String searchVal) {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
        return factory.getGoodDAO().getGoods(sortBy, from, number, cend, searchCol, searchVal);
    }
}
