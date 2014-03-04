/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface AppResources extends ClientBundle {
    public interface Styles extends CssResource {
        String clear();

        String list();

        String toolbar();

        String toolbarButton();

        String toolbarButtonDisabled();

        String tabs();

        String kindListElement();

        String kindListElementHovered();

        String entityTypeSelectorEmpty();

        String entityTypeSelector();

        String hiddenOverlay();

        String revealOverlay();

        String revealUnderOverlay();

        String kindHeaderElement();

        String firstTable();

        String secondTable();

        String pager();

        String entityListPanel();

        String entityDetailPanelVisibility();

        String expand();

        String collapse();

        String entityDetailPanel();

        String popup();

        String popupHeader();

        String close();

        String glassPanel();

        String lockedRow();

        String embeddedEntityProperties();

        String button();

        String authForm();

        String loginAjaxLoader();

        String northSection();

        String panelToggle();

        String profilerToggle();

        String delete();

        String record();

        String stop();

        String refresh();

        String pencil();

        String statementDetails();

        String statementImage();

        String statementImageRl();

        String statementImageDn();

        String className();

        String methodName();

        String main();

        String entityPanelTransitions();

        String flipped();

        String toggleKindsPanel();

        String filterText();

        String trashSmall();

        String importSmall();

        String trashSmallNoHover();

        String pencilSmall();

        String deselect();

        String deselectDisabled();

        String power();

        String checked();

        String booleanKey();

        String errorField();

        String importBtn();

        String exportBtn();

        String exportBtnDisabled();

        String chooseFileButton();

        String gray();

        String uploadLabel();

        String popupIcon();

        String buttonContainer();
    }

    @CssResource.NotStrict
    public Styles styles();

    public AuthStyles authStyles();

    ImageResource logo();

    ImageResource record();

    ImageResource stop();

    ImageResource delete();

    ImageResource refresh_up();

    ImageResource refresh_rl();

    ImageResource deselect_up();

    ImageResource deselect_rl();

    ImageResource deselect_dis();

    ImageResource pencil_up();

    ImageResource pencil_rl();

    ImageResource pencil_small();

    ImageResource create();

    ImageResource closeToggle();

    ImageResource trash_small_up();

    ImageResource import_small_up();

    ImageResource trash_small_rl();

    ImageResource expand_up();

    ImageResource expand_rl();

    ImageResource collapse_up();

    ImageResource collapse_rl();

    ImageResource splashLogo();

    ImageResource ajaxLoader30px();

    ImageResource cogplay();

    ImageResource deleteRl();

    ImageResource stopRl();

    ImageResource resize();

    ImageResource listDot();

    ImageResource listDotRl();

    ImageResource dropDownArrowUp();

    ImageResource dropDownArrowRl();

    ImageResource listGraph();

    ImageResource power();

    ImageResource importBtn();

    ImageResource exportBtn();
}
