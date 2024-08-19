package com.example.mealplanner.Network.NetworkListeners.SearchCategory;


import com.example.mealplanner.Model.Area.Area;

import java.util.List;

public interface AreaNetworkListener {
    void onAreaSuccess(List<Area> areas);
    void onAreaFailure(String message);
}
