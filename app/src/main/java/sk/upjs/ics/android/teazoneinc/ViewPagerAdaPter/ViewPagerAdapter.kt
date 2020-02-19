package sk.upjs.ics.android.teazoneinc.ViewPagerAdaPter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter (manager : FragmentManager):FragmentPagerAdapter(manager){

    private val fragmentList : MutableList<Fragment> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    fun addMananger(fragment: Fragment){
        fragmentList.add(fragment)
    }


}