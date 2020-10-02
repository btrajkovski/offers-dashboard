package com.north47.offersdashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardData {
    private Integer numOfElements;
    private List<String> offers;
}
