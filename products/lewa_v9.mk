# Inherit AOSP device configuration for v9.
$(call inherit-product, device/zte/v9/device_v9.mk)

# Inherit some common lewamod stuff.
$(call inherit-product, vendor/lewa/products/common_lewa.mk)

# Include GSM stuff
$(call inherit-product, vendor/lewa/products/gsm.mk)

#
# Setup device specific product configuration.
#
PRODUCT_NAME := lewa_v9
PRODUCT_BRAND := zte
PRODUCT_DEVICE := v9
PRODUCT_MODEL := V9
PRODUCT_MANUFACTURER := ZTE
PRODUCT_BUILD_PROP_OVERRIDES += PRODUCT_NAME=v9 BUILD_ID=GRJ22 BUILD_FINGERPRINT=google/passion/passion:2.3.4/GRJ22/121341:user/release-keys PRIVATE_BUILD_DESC="passion-user 2.3.4 GRJ22 121341 release-keys"

# Release name and versioning
PRODUCT_RELEASE_NAME := V9
PRODUCT_VERSION_DEVICE_SPECIFIC :=
-include vendor/lewa/products/common_versions.mk

#
# Copy legend specific prebuilt files
#


