# Inherit AOSP device configuration for showcasemtd.
$(call inherit-product, device/samsung/showcasemtd/full_showcasemtd.mk)

# Inherit some common lewamod stuff.
$(call inherit-product, vendor/lewa/products/common_lewa.mk)

#
# Setup device specific product configuration.
#
PRODUCT_NAME := lewa_showcasemtd
PRODUCT_BRAND := samsung
PRODUCT_DEVICE := showcasemtd
PRODUCT_MODEL := SCH-I500
PRODUCT_MANUFACTURER := samsung
PRODUCT_BUILD_PROP_OVERRIDES += PRODUCT_NAME=SCH-I500 TARGET_DEVICE=SCH-I500 BUILD_ID=GINGERBREAD BUILD_FINGERPRINT=uscc/SCH-I500/SCH-I500:2.3.4/GINGERBREAD/EH09:user/release-keys PRIVATE_BUILD_DESC="SCH-I500-user 2.3.4 GINGERBREAD EH09 release-keys"

# Extra showcasemtd overlay
PRODUCT_PACKAGE_OVERLAYS += vendor/lewa/overlay/showcasemtd

# Add the Torch app
PRODUCT_PACKAGES += Torch

# Release name and versioning
PRODUCT_RELEASE_NAME := Showcase
PRODUCT_VERSION_DEVICE_SPECIFIC :=
-include vendor/lewa/products/common_versions.mk

#
# Copy galaxys specific prebuilt files
#


