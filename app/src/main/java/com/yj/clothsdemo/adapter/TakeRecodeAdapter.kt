package com.yj.clothsdemo.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yj.clothsdemo.R
import com.yj.service.response.X
import kotlinx.android.synthetic.main.item_take_recode.view.*

/**
 * desc:
 * time: 2019/3/20
 * @author yinYin
 */
class TakeRecodeAdapter:BaseQuickAdapter<X,BaseViewHolder>(R.layout.item_take_recode) {
    override fun convert(helper: BaseViewHolder?, item: X?) {
        helper?.itemView?.apply {
            tv_time.text=item?.operateTime?:""
            tv_num.text=item?.boxNumber?:""
            tv_area.text=item?.cabinetName?:""
        }
    }
}