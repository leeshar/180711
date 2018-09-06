package com.myPro.pagination;

import java.util.*;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Page {
	private List list;
	private Pagination pagination;
}
