package com.cycrilabs.attachments.client.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data transfer object that represents a parsed receipt.
 */
@Getter
@Setter
@NoArgsConstructor
public class ReceiptDTO {
    /**
     * Receipt category. Can be any arbitrary string.
     */
    private String category;
    /**
     * Receipt description. Can be any arbitrary string.
     */
    private String description;
}
