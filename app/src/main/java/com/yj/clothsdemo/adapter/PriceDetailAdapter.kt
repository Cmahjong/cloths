package com.yj.clothsdemo.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yj.clothsdemo.R
import com.yj.service.response.Data6
import kotlinx.android.synthetic.main.item_price_detail.view.*

/**
 * desc:
 * time: 2019/3/21
 * @author yinYin
 */
class PriceDetailAdapter:BaseQuickAdapter<Data6, BaseViewHolder>(R.layout.item_price_detail)  {
    override fun convert(helper: BaseViewHolder?, item: Data6?) {
        helper?.itemView?.apply {
            tv_type_one.text=item?.className?:""
            tv_type_two.text=item?.detailName?:""
            tv_type_three.text=item?.number?:""
        }
        helper?.addOnClickListener(R.id.tv_delete)
        helper?.addOnClickListener(R.id.rl_type_one)
        helper?.addOnClickListener(R.id.rl_type_two)
        helper?.addOnClickListener(R.id.rl_type_three)
    }
}