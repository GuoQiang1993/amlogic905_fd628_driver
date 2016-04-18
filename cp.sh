ROOT=/devel/android/mlogic905
PWD=/devel/android/mlogic905/FD628/patch
cp -v $ROOT/common/arch/arm64/boot/dts/amlogic/mesongxbb.dtsi   $PWD/common/arch/arm64/boot/dts/amlogic/mesongxbb.dtsi.org
cp -v $ROOT/common/arch/arm64/boot/dts/amlogic/gxbb_p201.dts    $PWD/common/arch/arm64/boot/dts/amlogic/gxbb_p201.dts.org
cp -v $ROOT/common/drivers/amlogic/input/Kconfig                $PWD/common/drivers/amlogic/input/Kconfig.org
cp -v $ROOT/common/drivers/amlogic/input/Makefile               $PWD/common/drivers/amlogic/input/Makefile.org
cp -v $ROOT/common/arch/arm64/configs/meson64_defconfig         $PWD/common/arch/arm64/configs/meson64_defconfig.org


cp -v $ROOT/common/arch/arm64/boot/dts/amlogic/mesongxbb.dtsi   $ROOT/common/arch/arm64/boot/dts/amlogic/mesongxbb.dtsi.org
cp -v $ROOT/common/arch/arm64/boot/dts/amlogic/gxbb_p201.dts    $ROOT/common/arch/arm64/boot/dts/amlogic/gxbb_p201.dts.org
cp -v $ROOT/common/drivers/amlogic/input/Kconfig                $ROOT/common/drivers/amlogic/input/Kconfig.org
cp -v $ROOT/common/drivers/amlogic/input/Makefile               $ROOT/common/drivers/amlogic/input/Makefile.org
cp -v $ROOT/common/arch/arm64/configs/meson64_defconfig         $ROOT/common/arch/arm64/configs/meson64_defconfig.org
