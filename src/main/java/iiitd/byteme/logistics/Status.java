package iiitd.byteme.logistics;

import java.io.Serializable;

public enum Status implements Serializable {
    Cancelled, Denied, Delivered, OutForDelivery, Preparing, OrderReceived
}