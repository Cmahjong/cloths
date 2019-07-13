package com.yj.clothsdemo.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yj.clothsdemo.R
import com.yj.service.response.Price
import kotlinx.android.synthetic.main.item_price.view.*

/**
 * desc:
 * time: 2019/3/20
 * @author yinYin
 */
class PriceAdapter:BaseQuickAdapter<Price,BaseViewHolder>(R.layout.item_price) {
    override fun convert(helper: BaseViewHolder?, item: Price?) {
        helper?.itemView?.apply {
            tv_time.text=item?.pieceNumber?:""
            tv_area.text=item?.cabinetName?:""
            tv_handle.isEnabled=true
            if (item?.status == "2") {
                tv_handle.setBackgroundResource(R.drawable.delete1_bg)
                tv_handle.text = "定价"
            } else {
                tv_handle.setBackgroundResource(R.drawable.open_bg)
                tv_handle.text = "修改定价"
            }
        }
        helper?.addOnClickListener(R.id.tv_handle)
    }
}