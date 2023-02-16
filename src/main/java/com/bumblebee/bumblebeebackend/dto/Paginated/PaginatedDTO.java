package com.bumblebee.bumblebeebackend.dto.Paginated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/14/2023
 **/

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaginatedDTO {
    private Object list;
    private Long count;
}
