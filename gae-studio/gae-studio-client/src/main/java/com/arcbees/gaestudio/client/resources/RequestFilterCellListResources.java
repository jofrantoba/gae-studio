/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.resources;

import com.google.gwt.user.cellview.client.CellList;

public interface RequestFilterCellListResources extends CellList.Resources {
    interface ListStyle extends CellList.Style {
    }

    @Override
    @Source({"css/requestFilterCellListStyles.css"})
    ListStyle cellListStyle();
}
