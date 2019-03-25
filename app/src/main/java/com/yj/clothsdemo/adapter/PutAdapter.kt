package com.yj.clothsdemo.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yj.clothsdemo.R
import com.yj.service.response.PutOrder
import kotlinx.android.synthetic.main.item_put.view.*

/**
 * desc:
 * time: 2019/3/20
 * @author yinYin
 */
class PutAdapter : BaseQuickAdapter<PutOrder, BaseViewHolder>(R.layout.item_put) {
    override fun convert(helper: BaseViewHolder?, item: PutOrder?) {
        helper?.itemView?.apply {
            tv_time.text = item?.pieceNumber ?: ""
            tv_num.text = item?.boxNumber ?: "请选择"
            tv_open.isEnabled=true
            tv_sure.isEnabled = item?.status == "3"
            tv_num.isEnabled = item?.status == "3"
        }
        helper?.addOnClickListener(R.id.tv_open)
        helper?.addOnClickListener(R.id.tv_sure)
        helper?.addOnClickListener(R.id.tv_num)
    }
}